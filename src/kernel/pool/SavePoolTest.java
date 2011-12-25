package kernel.pool;

import junit.framework.Assert;
import kernel.chunk.ChunkNotSavedException;
import kernel.chunk.ChunkSaver;
import kernel.util.Matrix;
import org.junit.Test;

import java.io.OutputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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
        SavePool pool = new SavePool(new ChunkSaver()
        {
            public void save(Matrix data, OutputStream stream) throws ChunkNotSavedException
            {
                latch.countDown();
            }
        }, "", 1);
        
        for (int i = 0; i < 4; i++)
        {
            ChunkSaveInfo info = new ChunkSaveInfo(i, 1, new Matrix(1, 1));
            pool.enqueue(info);
        }

        latch.await(1, TimeUnit.SECONDS);
        Assert.assertEquals(0, latch.getCount());
    }


}
