package kernel.source;

import com.sun.corba.se.pept.transport.ReaderThread;
import junit.framework.Assert;
import kernel.chunk.Chunk;
import kernel.chunk.ChunkLoader;
import kernel.chunk.ChunkNotLoadedException;
import kernel.chunk.LOD;
import kernel.pool.LoadPool;
import org.junit.Test;

import javax.xml.crypto.Data;

import static org.mockito.Mockito.*;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 14.11.11
 * Time: 2:24
 */
public class CacheTest
{
    @Test
    public void testGet() throws Exception
    {
        LOD lod = mock(LOD.class);
        when(lod.getWidth()).thenReturn(2);
        when(lod.getHeight()).thenReturn(2);
        when(lod.getChunkEdge()).thenReturn(3);

        short val = 0;
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++)
                when(lod.getChunk(i, j)).thenReturn(new MockChunk(val++, 3));

        Cache cache = new Cache(lod, mock(LoadPool.class));

        Assert.assertEquals(0, cache.get(2, 2));
        Assert.assertEquals(1, cache.get(2, 3));
        Assert.assertEquals(2, cache.get(3, 2));
        Assert.assertEquals(3, cache.get(3, 3));
    }

    @Test
    public void testGetWidthAndHeight() throws Exception
    {
        LOD lod = mock(LOD.class);
        when(lod.getWidth()).thenReturn(2);
        when(lod.getHeight()).thenReturn(3);
        when(lod.getChunkEdge()).thenReturn(7);

        Cache cache = new Cache(lod, mock(LoadPool.class));

        Assert.assertEquals(14, cache.getWidth());
        Assert.assertEquals(21, cache.getHeight());
    }

    private class MockChunk extends Chunk
    {
        private short data[][];

        public MockChunk(short val, int edge)
        {
            data = new short[edge][edge];
            for (int i = 0; i < edge; i++)
                for (int j = 0; j < edge; j++)
                    data[i][j] = val;
        }

        @Override
        public boolean isLoaded()
        {
            return true;
        }

        @Override
        public short[][] getData()
        {
            return data;
        }
    }
}
