package kernel.pool;

import kernel.chunk.Chunk;
import kernel.chunk.ChunkLoader;
import kernel.chunk.ChunkNotLoadedException;
import kernel.pool.Pool;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 07.11.11
 * Time: 19:26
 */
public class LoadPool implements Pool<Chunk>
{
    private Queue<Chunk> chunks = new LinkedList<Chunk>();
    private ExecutorService workers;
    @Nullable private LoadListener listener;
    private ChunkLoader loader;
    private int maxLoaded;

    public LoadPool(ChunkLoader loader, int threads, int maxLoaded)
    {
        workers = Executors.newFixedThreadPool(threads);
        this.loader = loader;
        this.maxLoaded = maxLoaded;
    }

    public void loadImmediately(Chunk chunk) throws ChunkNotLoadedException
    {
        if (!chunk.isLoaded())
        {
            synchronized (this)
            {
                ensureCapacity();
            }
            chunk.load(loader);
        }
    }

    private void ensureCapacity()
    {
        if (chunks.size() > maxLoaded)
            chunks.remove().unload();
    }

    public synchronized void enqueue(Chunk chunk)
    {
        ensureCapacity();
        chunks.add(chunk);
        workers.execute(new LoadTask());
    }

    protected synchronized Chunk pop()
    {
        return chunks.poll();
    }

    public void addChangeListener(LoadListener listener)
    {
        this.listener = listener;
    }

    private synchronized void dispatchLoaded(Chunk chunk)
    {
        if (listener != null)
            listener.onChunkLoaded(chunk);
    }

    private class LoadTask implements Runnable
    {
        public void run()
        {
            Chunk chunk = pop();
            if (chunk != null)
            {
                try
                {
                    chunk.load(loader);
                    dispatchLoaded(chunk);
                }
                catch (ChunkNotLoadedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public static interface LoadListener
    {
        void onChunkLoaded(Chunk chunk);
    }
}
