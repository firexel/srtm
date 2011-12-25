package kernel.chunk;

import kernel.util.Matrix;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 13.11.11
 * Time: 0:52
 */
public class DefaultSrtmLoader implements ChunkLoader
{
    private int edge;

    public DefaultSrtmLoader(int edge)
    {
        this.edge = edge;
    }

    public Matrix load(RandomAccessFile file, int x, int y, int width, int height) throws ChunkNotLoadedException
    {
        if (x + width - 1 > edge)
            throw new ChunkNotLoadedException("Width too large :" + (x + width - 1));

        if (y + height - 1 > edge)
            throw new ChunkNotLoadedException("Height too large :" + (y + height - 1));

        short[][] result = new short[height][width];
        byte[] row = new byte[width * 2];
        try
        {
            for (int iy = y; iy < y + height; iy++)
            {
                file.seek((iy * edge + x) * 2);
                file.read(row);
                for (int ix = 0; ix < width; ix++)
                {
                    int sh = row[ix * 2];
                    sh = sh << 8;
                    sh |= row[ix * 2 + 1];
                    result[iy - y][ix] = (short) sh;
                }
            }
            return new Matrix(width, height, result);
        }
        catch (IOException e)
        {
            throw new ChunkNotLoadedException(e);
        }
    }
}
