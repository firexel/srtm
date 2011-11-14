package kernel.chunk;

import com.sun.istack.internal.NotNull;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 31.10.11
 * Time: 6:17
 */
public class Chunk
{
    public String filename, path;
    private int edge;
    private short data[][];
    private static final int BUFFER_SIZE = 1024 * 8;

    public Chunk()
    {
    }

    public Chunk(String path, String filename, int edge)
    {
        this.path = path;
        this.filename = filename;
        this.edge = edge;
    }

    public Chunk(int number, short[][] data)
    {
        this.filename = new StringBuilder().append(number).append(".hgt").toString();
        this.data = data;
    }

    public synchronized boolean isLoaded()
    {
        return data != null;
    }

    public synchronized void unload()
    {
        data = null;
    }

    public synchronized void load(ChunkLoader loader) throws ChunkNotLoadedException
    {
        String name = path + File.separator + filename;

        FileInputStream fis;
        try
        {
            fis = new FileInputStream(name);
        }
        catch (FileNotFoundException e)
        {
            throw new ChunkNotLoadedException(e);
        }

        BufferedInputStream bis = new BufferedInputStream(fis, BUFFER_SIZE);
        try
        {
            data = loader.load(bis);
            bis.close();
            fis.close();
        }
        catch (IOException e)
        {
            throw new ChunkNotLoadedException(e);
        }
    }

    public void save(String path, ChunkSaver saver)
    {
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
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
        finally
        {
            try
            {
                bos.flush();
                bos.close();
                fos.close();
            }
            catch (Exception e)
            {
            }
        }
    }

    public short[][] getData()
    {
        return data;
    }
}
