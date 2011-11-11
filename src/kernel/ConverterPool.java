package kernel;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 28.10.11
 * Time: 8:05
 */
public class ConverterPool<Input, Output> implements Converter<Input, Output>
{
    @NotNull private Queue<Input> requests = new ConcurrentLinkedQueue<Input>();
    @NotNull private Map<Input, Output> cache = new ConcurrentHashMap<Input, Output>();
    @NotNull private AtomicBoolean stopFlag = new AtomicBoolean(true);
    @NotNull private Converter<Input, Output> converter;
    @Nullable private ConversionThread thread;

    public ConverterPool(@NotNull Converter<Input, Output> converter)
    {
        this.converter = converter;
    }

    public synchronized void start()
    {
        if (thread == null)
        {
            stopFlag.set(false);
            thread = new ConversionThread();
            thread.start();
        }
    }

    public synchronized void stop()
    {
        if (thread != null)
        {
            thread = null;
            stopFlag.set(true);
        }
    }

    public void clear()
    {
        cache.clear();
        requests.clear();
        stop();
    }

    public Output convert(Input request)
    {
        Output output = cache.get(request);
        if (output == null)
        {
            output = converter.convert(request);
            if (output != null)
                cache.put(request, output);
        }
        else
        {
        }
        return output;
    }

    public void request(Input request)
    {
        if(!cache.containsKey(request))
            requests.offer(request);

        if(thread == null && !stopFlag.get())
            start();
    }

    public void notifyLowMemory()
    {
        stop();
        clear();
    }

    private class ConversionThread extends Thread
    {
        @Override
        public void run()
        {
            while (!stopFlag.get())
            {
                Input request = requests.poll();
                if (request == null)
                {
                    thread = null;
                    break;
                }
                Output output = converter.convert(request);
                if (output != null)
                    cache.put(request, output);
            }
        }
    }

}
