package kernel.chunk;

import kernel.util.Matrix;

import java.io.RandomAccessFile;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 13.11.11
 * Time: 0:43
 */
public interface ChunkLoader
{
    Matrix load(RandomAccessFile file, int x, int y, int width, int height) throws ChunkNotLoadedException;
}
