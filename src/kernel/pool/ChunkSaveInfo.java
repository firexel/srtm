package kernel.pool;

import kernel.chunk.Chunk;

/**
* Created by IntelliJ IDEA.
* User: alex
* Date: 28.11.11
* Time: 4:22
*/
public class ChunkSaveInfo
{
    public short[][] data;
    public int chunkNumber;
    public int chunkEdge;
    
    public ChunkSaveInfo(int chunkNumber, int chunkEdge, short[][] data)
    {
        this.chunkNumber = chunkNumber;
        this.data = data;
        this.chunkEdge = chunkEdge;
    }
}
