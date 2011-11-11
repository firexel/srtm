package kernel;

import java.io.File;
import java.io.FilenameFilter;
import java.util.LinkedList;
import java.util.List;

/**
* Created by IntelliJ IDEA.
* User: alex
* Date: 11.11.11
* Time: 23:37
*/
public class LOD
{
    private static final FilenameFilter HGT_FILTER = new FilenameFilter()
    {
        public boolean accept(File dir, String name)
        {
            return name.endsWith(".hgt");
        }
    };
    
    private Chunk chunks[][];
    private int width, height;
    private int chunkEdge;

    private LOD(int chunkSize, List<Chunk> chunkList)
    {
        width = 360 / chunkSize;
        height = 180 / chunkSize;

        chunks = new Chunk[width][height];
        for (int x = 0; x < width; x++)
            chunks[x] = new Chunk[height];

        for (Chunk chunk : chunkList)
        {
            int x = chunk.azimuthAngle + 180;
            int y = chunk.zenithAngle + 90;
            chunks[x][y] = chunk;
        }
    }

    public LOD(int width, int height, int edge, Chunk chunks[][])
    {
        this.width = width;
        this.height = height;
        this.chunkEdge = edge;
        this.chunks = chunks;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public Chunk getChunk(int x, int y)
    {
        return chunks[x][y];
    }

    public int getChunkEdge()
    {
        return chunkEdge;
    }

    public static LOD parseSrtm2(String path)
    {
        int width = 360;
        int height = 180;
        int edge = 1201;

        Chunk chunks[][] = new Chunk[width][height];
        for (int x = 0; x < width; x++)
            chunks[x] = new Chunk[height];

        File folder = new File(path);
        for (String filename : folder.list(HGT_FILTER))
        {
            Chunk chunk = new Chunk(path, filename, true, 1201);
            chunks[chunk.azimuthAngle + width/2][chunk.zenithAngle + height/2] = chunk;
        }

        return new LOD(width, height, edge-1, chunks);
    }
}
