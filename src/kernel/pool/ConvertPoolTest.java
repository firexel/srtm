package kernel.pool;

import junit.framework.Assert;
import kernel.source.DataSource;
import kernel.util.Matrix;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 12.11.11
 * Time: 21:57
 */
public class ConvertPoolTest
{
    @Test
    public void testLaunch() throws InterruptedException
    {
        final CountDownLatch latch = new CountDownLatch(4);
        Pool<ChunkSaveInfo> mockSavePool = new Pool<ChunkSaveInfo>()
        {
            public void enqueue(ChunkSaveInfo object)
            {
                latch.countDown();
            }
        };
        DataSource source = new DataSource()
        {
            public Matrix get(int x, int y, int width, int height)
            {
                return new Matrix(width, height, 1);
            }

            public int getWidth()
            {
                return 2;
            }

            public int getHeight()
            {
                return 2;
            }
        };
        ConvertPool convertPool = new ConvertPool(source, mockSavePool, 1);
        convertPool.start(3);
        latch.await(1, TimeUnit.SECONDS);
        Assert.assertEquals(0, latch.getCount());
    }
}
