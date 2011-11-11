package kernel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 12.11.11
 * Time: 0:40
 */
public class ConvertPool
{
    private DataSource source;
    private int edge;
    private ExecutorService executorService;
    private int chunkNumber = 0;
    private int maxChunkIndex;
    private int width;
    private int height;
    private LOD lod;
    private Pool<Chunk> savePool;

    public ConvertPool(DataSource source, Pool<Chunk> savePool, int edge, int threads)
    {
        this.edge = edge;
        this.source = source;
        this.savePool = savePool;

        width = source.getWidth() / edge;
        height = source.getHeight() / edge;

        maxChunkIndex = (width - 1) * (height - 1);
        lod = LOD.createEmpty(width, height, edge);

        executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < threads; i++)
            executorService.execute(new Converter());
    }

    private synchronized int getNextChunkNumber()
    {
        if(chunkNumber > maxChunkIndex)
            return -1;

        return chunkNumber++;
    }

    private class Converter implements Runnable
    {
        public void run()
        {
            int chunk;
            while ((chunk = getNextChunkNumber()) != -1)
            {
                int x = chunk / width;
                int y = chunk % height;

                int offsetX = x * width;
                int offsetY = y * height;

                short data[][] = new short[edge][edge];
                short value;

                boolean empty = true;

                for(int i=0; i<edge; i++)
                {
                    for(int j=0; j<edge; j++)
                    {
                        value = source.get(i+offsetX, j+offsetY);
                        data[i][j] = value;
                        if(value != 0)
                            empty = false;
                    }
                }

                Chunk chunkObj = new Chunk(chunk, data);

                if(!empty)
                    savePool.enqueue(chunkObj);
            }
        }
    }
}
