package kernel.chunk;

import kernel.util.Matrix;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 13.11.11
 * Time: 1:00
 */
public class ConvertedChunkIO implements ChunkLoader, ChunkSaver
{
    public void save(Matrix data, OutputStream stream) throws ChunkNotSavedException
    {
        DataOutputStream dos = new DataOutputStream(stream);
        try
        {
            dos.writeInt(data.getWidth());
            for(int y=0; y<data.getHeight(); y++)
                for(int x=0; x<data.getWidth(); x++)
                    dos.writeShort(data.get(x, y));

            dos.close();
        }
        catch (IOException e)
        {
            throw new ChunkNotSavedException(e);
        }
    }

    public Matrix load(RandomAccessFile file, int x, int y, int width, int height) throws ChunkNotLoadedException
    {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
