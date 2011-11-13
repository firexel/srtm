package kernel.pool;

import kernel.chunk.Chunk;
import kernel.chunk.ChunkSaver;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 13.11.11
 * Time: 22:50
 */
public class SavePool implements Pool<Chunk>
{
    private ExecutorService executorService;
    private Queue<Chunk> chunks = new LinkedList<Chunk>();
    private ChunkSaver saver;
    private String path;

    public SavePool(ChunkSaver saver, String path, int threads)
    {
        this.saver = saver;
        this.path = path;
        executorService = Executors.newFixedThreadPool(threads);
    }

    public synchronized void enqueue(Chunk object)
    {
        chunks.add(object);
        executorService.execute(new SaveChunkTask());
    }

    @Nullable
    private synchronized Chunk getNexChunk()
    {
        return chunks.poll();
    }

    private class SaveChunkTask implements Runnable
    {
        public void run()
        {
            Chunk chunk = getNexChunk();
            if(chunk != null && chunk.isLoaded())
            {
                chunk.save(path, saver);
                chunk.unload();
            }
        }
    }
}
