package kernel.chunk;

import kernel.util.Matrix;

import java.io.OutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 13.11.11
 * Time: 0:46
 */
public interface ChunkSaver
{
    void save(Matrix data, OutputStream stream) throws ChunkNotSavedException;
}
