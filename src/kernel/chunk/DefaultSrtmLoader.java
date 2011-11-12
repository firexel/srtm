package kernel.chunk;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 13.11.11
 * Time: 0:52
 */
public class DefaultSrtmLoader implements ChunkLoader
{
    private int edge;

    public DefaultSrtmLoader()
    {
        edge = 1201;
    }

    public DefaultSrtmLoader(int edge)
    {
        this.edge = edge;
    }

    public short[][] load(InputStream stream) throws ChunkNotLoadedException
    {
        short data[][] = new short[edge][edge];
        DataInputStream dis = new DataInputStream(stream);
        try
        {
            for (int x = 0; x < edge; x++)
                for (int y = 0; y < edge; y++)
                    data[y][x] = dis.readShort();

            return data;
        }
        catch (IOException ex)
        {
            throw new ChunkNotLoadedException(ex);
        }
    }
}
