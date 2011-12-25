package kernel.chunk;

import kernel.source.DataSource;
import kernel.util.Matrix;
import org.jetbrains.annotations.Nullable;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 31.10.11
 * Time: 6:17
 */
public class Chunk implements DataSource
{
    private String filename, path;
    private int edge;
    private ChunkLoader loader;
    private static final int BUFFER_SIZE = 8 * 1024;

    public Chunk(String path, String filename, int edge, @Nullable ChunkLoader chunkLoader)
    {
        this.path = path;
        this.filename = filename;
        this.edge = edge;
        this.loader = chunkLoader;
    }

    public Chunk(int number, int edge)
    {
        this("", number + ".hgt", edge, null);
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public void save(ChunkSaver saver, Matrix data)
    {
        FileOutputStream fos;
        BufferedOutputStream bos;
        try
        {
            String nPath = path + File.separator + filename;
            fos = new FileOutputStream(nPath);
            bos = new BufferedOutputStream(fos, BUFFER_SIZE);

            saver.save(data, bos);
            bos.flush();
            bos.close();
            fos.close();
        }
        catch (IOException e)
        {
            System.err.println("Error during saving chunk " + filename);
        }
        catch (ChunkNotSavedException e)
        {
            System.err.println("Error during saving chunk " + filename);
        }
    }

    public synchronized Matrix get(int x, int y, int width, int height)
    {
        try
        {
            RandomAccessFile file = new RandomAccessFile(path + File.separator + filename, "r");
            Matrix data = loader.load(file, x, y, width, height);
            file.close();
            return data;
        }
        catch (FileNotFoundException e)
        {
            throw new ChunkNotLoadedException(e);
        }
        catch (IOException e)
        {
            throw new ChunkNotLoadedException(e);
        }
    }

    public int getWidth()
    {
        return edge;
    }

    public int getHeight()
    {
        return edge;
    }
}
