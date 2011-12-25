package kernel.chunk;

import kernel.source.DataSource;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FilenameFilter;

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

    private LOD(int width, int height, int edge, Chunk chunks[][])
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

        ChunkLoader loader = new DefaultSrtmLoader(edge);
        Chunk chunks[][] = new Chunk[height][width];

        File folder = new File(path);
        for (String filename : folder.list(HGT_FILTER))
        {
            Chunk chunk = new Chunk(path, filename, edge, loader);

            int zenithAngle = Integer.parseInt(filename.substring(1, 3), 10);
            if (filename.charAt(0) == 'N')
                zenithAngle *= -1;

            int azimuthAngle = Integer.parseInt(filename.substring(4, 7), 10);
            if (filename.charAt(3) == 'W')
                azimuthAngle *= -1;

            chunks[zenithAngle + height / 2][azimuthAngle + width / 2] = chunk;
        }

        return new LOD(width, height, edge, chunks);
    }

    public static LOD createEmpty(int width, int height, int edge)
    {
        Chunk chunks[][] = new Chunk[width][height];
        for (int x = 0; x < width; x++)
            chunks[x] = new Chunk[height];

        return new LOD(width, height, edge, chunks);
    }

    public DataSource[][] getGrid()
    {
        return chunks;
    }
}
