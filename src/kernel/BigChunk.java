package kernel;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 31.10.11
 * Time: 6:18
 */
public class BigChunk extends Chunk
{
    private static final int TOP_LEFT=0, TOP_RIGHT=1, BOTTOM_LEFT=2, BOTTOM_RIGHT=3;
    private Chunk chunks[] = new Chunk[4];

    public BigChunk(Chunk topLeft, Chunk topRight, Chunk bottomLeft, Chunk bottomRight)
    {
        chunks[TOP_LEFT] = topLeft;
        chunks[TOP_RIGHT] = topRight;
        chunks[BOTTOM_LEFT] = bottomLeft;
        chunks[BOTTOM_RIGHT] = bottomRight;

        for(Chunk chunk : chunks)
        {
            if(chunk != null)
            {
                this.size = chunk.size * 2;
                break;
            }
        }
    }
}
