package kernel.pool;

import junit.framework.Assert;
import kernel.chunk.Chunk;
import kernel.source.DataSource;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

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
        Pool<Chunk> mockSavePool = new Pool<Chunk>()
        {
            public void enqueue(Chunk object)
            {
                latch.countDown();
            }
        };
        DataSource source = mock(DataSource.class);
        when(source.get(anyInt(), anyInt())).thenReturn((short)1);
        when(source.getHeight()).thenReturn(2);
        when(source.getWidth()).thenReturn(2);
        ConvertPool convertPool = new ConvertPool(source, mockSavePool, 1);
        convertPool.start(3);
        latch.await(1, TimeUnit.SECONDS);
        Assert.assertEquals(0, latch.getCount());
    }
}
