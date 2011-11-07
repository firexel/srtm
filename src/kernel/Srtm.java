package kernel;

import com.sun.istack.internal.NotNull;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 31.10.11
 * Time: 5:28
 */
public class Srtm
{
    private String path;
    private List<Chunk> chunks;
    private LOD topLod;

    public Srtm(String path)
    {
        this.path = path;
        load();
        topLod = new LOD(1, chunks);
    }

    private void load()
    {
        chunks = new LinkedList<Chunk>();
        File folder = new File(path);
        for (String filename : folder.list())
            chunks.add(new Chunk(path, filename));
    }

    @NotNull
    public List<Chunk> enumerate()
    {
        return chunks;
    }

    public class LOD
    {
        Chunk chunks[][];
        int width, height;

        public LOD(int chunkSize, List<Chunk> chunkList)
        {
            width = 360 / chunkSize;
            height = 180 / chunkSize;

            chunks = new Chunk[width][height];
            for (int x = 0; x < width; x++)
                chunks[x] = new Chunk[height];

            for (Chunk chunk : chunkList)
                chunks[chunk.azimuthAngle + 180][chunk.zenithAngle + 90] = chunk;
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
    }
}
