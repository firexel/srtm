package kernel;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 11.11.11
 * Time: 21:40
 */
public class Cache implements DataSource
{
    private LOD lod;
    private LoadPool loadPool;
    private int height;
    private int width;
    private int edge;

    public Cache(LOD lod, LoadPool loadPool)
    {
        this.lod = lod;
        this.loadPool = loadPool;
        height = lod.getHeight();
        width = lod.getWidth();
        edge = lod.getChunkEdge();
    }

    public short get(int x, int y)
    {
        int cx = x / edge;
        int cy = y / edge;

        Chunk chunk = lod.getChunk(cx, cy);
        if (chunk != null)
        {
            if(!chunk.isLoaded())
            {
                try
                {
                    chunk.load();
                }
                catch (IOException e)
                {
                    throw new IllegalStateException("Unable to load chunk " + chunk);
                }

                prefetch(cx + 1, cy);
                prefetch(cx, cy + 1);
                prefetch(cx + 1, cy +1);
            }
            x = clip(x, 0, width * edge - 1);
            y = clip(y, 0, height * edge - 1);
            return chunk.getData()[x][y];
        }
        return 0;
    }

    public int getWidth()
    {
        return lod.getWidth();
    }

    public int getHeight()
    {
        return lod.getHeight();
    }

    public boolean isAvailable(int x, int y)
    {
        int cx = x / edge;
        int cy = y / edge;
        return lod.getChunk(cx, cy) != null;
    }

    private void prefetch(int x, int y)
    {
        if(x < width && x >= 0 && y >= 0 && y < height)
        {
            Chunk chunk = lod.getChunk(x, y);
            if(chunk != null && !chunk.isLoaded())
                loadPool.enqueue(chunk);
        }
    }

    private int clip(int val, int min, int max)
    {
        if (val < min)
            return min;

        if (val > max)
            return max;

        return val;
    }
}
