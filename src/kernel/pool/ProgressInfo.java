package kernel.pool;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 28.11.11
 * Time: 8:12
 */
public class ProgressInfo
{
    private int chunksTotal;
    private int chunksCovered;
    private int chunksSaved;

    public ProgressInfo(int chunksTotal)
    {
        this.chunksTotal = chunksTotal;
    }

    public synchronized int getChunksTotal()
    {
        return chunksTotal;
    }

    public synchronized int getChunksCovered()
    {
        return chunksCovered;
    }
    
    public synchronized int getChunksSaved()
    {
        return chunksSaved;
    }
    
    public synchronized void incrementCovered()
    {
        chunksCovered++;
    }

    public synchronized void incrementSaved()
    {
        chunksSaved++;
    }
}
