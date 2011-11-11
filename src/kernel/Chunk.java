package kernel;

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
    private static final int BUFFER_SIZE = 8 * 1024;
    public String filename, path;
    public int zenithAngle, azimuthAngle;
    public float size;

    private boolean bigEndian;
    private int edge;
    private short data[][];

    public Chunk()
    {
    }

    public Chunk(String path, String filename, boolean bigEndian, int edge)
    {
        this.path = path;
        this.filename = filename;
        this.size = 1;
        this.bigEndian = bigEndian;
        this.edge = edge;

        zenithAngle = Integer.parseInt(filename.substring(1, 3), 10);
        if (filename.charAt(0) == 'N')
            zenithAngle *= -1;

        azimuthAngle = Integer.parseInt(filename.substring(4, 7), 10);
        if (filename.charAt(3) == 'W')
            azimuthAngle *= -1;
    }

    public Chunk(int number,  short[][] data)
    {
        this.filename = new StringBuilder().append(number).append(".hgt").toString();
        this.data = data;
    }

    public synchronized boolean isLoaded()
    {
        return data != null;
    }

    public void unload()
    {
        data = null;
    }

    public synchronized void load() throws IOException
    {
        String name = path + File.separator + filename;

        // open file
        FileInputStream fis = new FileInputStream(name);
        BufferedInputStream bis = new BufferedInputStream(fis);
        DataInputStream dis = new DataInputStream(bis);

        data = new short[edge][edge];
        try
        {
            for (int x = 0; x < edge; x++)
                for (int y = 0; y < edge; y++)
                    data[y][x] = dis.readShort();
        }
        catch (IOException ex)
        {
            System.err.println("Error during loading chunk " + name);
        }
    }

    public void save() throws IOException
    {
        byte bytes[] = new byte[BUFFER_SIZE];

        // open file
        FileInputStream fis = new FileInputStream(path + File.separator + filename);
        BufferedInputStream bis = new BufferedInputStream(fis);

        // open zip archive
        String nPath = path + File.separator + filename + ".zip";
        FileOutputStream fos = new FileOutputStream(nPath);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        ZipOutputStream zis = new ZipOutputStream(bos);

        // convert
        ZipEntry entry = new ZipEntry(filename);
        zis.putNextEntry(entry);
        int count;
        while ((count = bis.read(bytes)) > 0)
            zis.write(bytes, 0, count);

        // close zip archive
        zis.closeEntry();
        zis.close();
        bos.flush();
        bos.close();
        fos.close();

        // close file
        bis.close();
        fis.close();
    }

    public short[][] getData()
    {
        return data;
    }

    @NotNull
    private byte[] convert(byte bytes[], int count)
    {
        int length = Math.min(count, bytes.length);
        for (int i = 0; i < length; i += 2)
        {
            byte b = bytes[i + 1];
            bytes[i + 1] = bytes[i];
            bytes[i] = b;
        }
        return bytes;
    }
}
