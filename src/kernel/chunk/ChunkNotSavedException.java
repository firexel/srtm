package kernel.chunk;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 13.11.11
 * Time: 0:48
 */
public class ChunkNotSavedException extends Exception
{
    public ChunkNotSavedException(Throwable e)
    {
        super(e);
    }
}
