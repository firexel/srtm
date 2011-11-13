package kernel.pool;

import com.sun.rowset.internal.InsertRow;
import junit.framework.Assert;
import kernel.chunk.Chunk;
import kernel.chunk.ChunkNotSavedException;
import kernel.chunk.ChunkSaver;
import org.junit.Test;

import java.io.OutputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 13.11.11
 * Time: 22:56
 */
public class SavePoolTest
{
    private CountDownLatch latch = new CountDownLatch(4);

    @Test
    public void testEnqueue() throws Exception
    {
        SavePool pool = new SavePool(mock(ChunkSaver.class), "", 1);
        for(int i = 0; i<4; i++)
            pool.enqueue(new MockChunk());
        
        latch.await(1, TimeUnit.SECONDS);
        Assert.assertEquals(0, latch.getCount());
    }

    private class MockChunk extends Chunk
    {
        @Override
        public void save(String path, ChunkSaver saver)
        {
            latch.countDown();
        }

        @Override
        public boolean isLoaded()
        {
            return true;
        }
    }
}
