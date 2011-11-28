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
public class SavePool implements Pool<ChunkSaveInfo>
{
    private ExecutorService executorService;
    private Queue<ChunkSaveInfo> chunks = new LinkedList<ChunkSaveInfo>();
    private ChunkSaver saver;
    private String folder;

    public SavePool(ChunkSaver saver, String folder, int threads)
    {
        this.folder = folder;
        this.saver = saver;
        executorService = Executors.newFixedThreadPool(threads);
    }

    public synchronized void enqueue(ChunkSaveInfo object)
    {
        chunks.add(object);
        executorService.execute(new SaveChunkTask());
        if(chunks.size() > 200)
            System.out.printf("SavePool overloaded (%d chunks in queue)", chunks.size());
    }

    @Nullable
    private synchronized ChunkSaveInfo getNexChunk()
    {
        return chunks.poll();
    }

    private class SaveChunkTask implements Runnable
    {
        public void run()
        {
            ChunkSaveInfo info = getNexChunk();
            if(info != null && info.data != null && saver != null)
            {
                Chunk chunk = new Chunk(folder, info.chunkNumber + ".hgt", info.chunkEdge, null);
                chunk.save(saver, info.data);
            }
        }
    }

}
