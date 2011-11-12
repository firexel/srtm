package kernel.chunk;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import junit.framework.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import static org.mockito.Mockito.*;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 13.11.11
 * Time: 1:47
 */
public class ConvertedChunkIOTest
{
    private byte[] bytes = new byte[]{ // big-endian
            0, 0, 0, 2, // int 2
            0, 1,       // short 1
            0, 2,
            0, 3,
            0, 4
    };

    private short[][] data = new short[][]{
            {1, 2},
            {3, 4}
    };

    @Test
    public void testLoad() throws Exception
    {
        ConvertedChunkIO loader = new ConvertedChunkIO();
        short data[][] = loader.load(new ByteArrayInputStream(bytes));
        Assert.assertEquals(1, data[0][0]);
        Assert.assertEquals(2, data[1][0]);
        Assert.assertEquals(3, data[0][1]);
        Assert.assertEquals(4, data[1][1]);
    }

    @Test
    public void testSave() throws Exception
    {
        ConvertedChunkIO saver = new ConvertedChunkIO();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        saver.save(data, stream);
        Assert.assertTrue(Arrays.equals(this.bytes, stream.toByteArray()));
    }

    @Test(expected = ChunkNotLoadedException.class)
    public void testLoadingFail() throws Exception
    {
        ConvertedChunkIO loader = new ConvertedChunkIO();
        loader.load(new ByteArrayInputStream(new byte[]{1}));
    }

    @Test(expected = ChunkNotSavedException.class)
    public void testSaveFail() throws Exception
    {
        ConvertedChunkIO saver = new ConvertedChunkIO();
        OutputStream stream = mock(OutputStream.class);
        doThrow(new IOException("Hehe")).when(stream).write(anyInt());
        saver.save(data, stream);
    }
}
