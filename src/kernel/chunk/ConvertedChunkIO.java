package kernel.chunk;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 13.11.11
 * Time: 1:00
 */
public class ConvertedChunkIO implements ChunkLoader, ChunkSaver
{
    public short[][] load(InputStream stream) throws ChunkNotLoadedException
    {
        DataInputStream dis = new DataInputStream(stream);
        try
        {
            int edge = dis.readInt();
            short data[][] = new short[edge][edge];

            for (int x = 0; x < edge; x++)
                for (int y = 0; y < edge; y++)
                    data[y][x] = dis.readShort();

            return data;
        }
        catch (IOException ex)
        {
            throw new ChunkNotLoadedException(ex);
        }
        finally
        {
            try{ dis.close(); }
            catch (IOException e){}
        }
    }

    public void save(short[][] data, OutputStream stream) throws ChunkNotSavedException
    {
        DataOutputStream dos = new DataOutputStream(stream);
        try
        {
            int length = data.length;
            dos.writeInt(length);
            for(int x=0; x< length; x++)
                for(int y=0; y< length; y++)
                    dos.writeShort(data[x][y]);
        }
        catch (IOException e)
        {
            throw new ChunkNotSavedException(e);
        }
    }
}
