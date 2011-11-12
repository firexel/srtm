package kernel.chunk;

import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 13.11.11
 * Time: 0:43
 */
public interface ChunkLoader
{
    short[][] load(InputStream stream) throws ChunkNotLoadedException;
}
