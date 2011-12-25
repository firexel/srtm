package kernel.chunk;

import junit.framework.Assert;
import kernel.util.Matrix;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

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
    public void testSave() throws Exception
    {
        ConvertedChunkIO saver = new ConvertedChunkIO();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        saver.save(new Matrix(2, 2, data), stream);
        Assert.assertTrue(Arrays.equals(this.bytes, stream.toByteArray()));
    }
}
