package kernel.chunk;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 13.11.11
 * Time: 0:45
 */
public class ChunkNotLoadedException extends RuntimeException
{
    public ChunkNotLoadedException(String message)
    {
        super(message);
    }

    public ChunkNotLoadedException(Throwable e)
    {
        super(e);
    }
}
