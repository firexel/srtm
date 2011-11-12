package kernel.chunk;

import junit.framework.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 13.11.11
 * Time: 1:36
 */
public class DefaultSrtmLoaderTest
{
    @Test
    public void testLoad() throws Exception
    {
        byte bytes[] = new byte[]{ // 1,2,3,4 shorts in big-endian
                0, 1,
                0, 2,
                0, 3,
                0, 4
        };

        DefaultSrtmLoader loader = new DefaultSrtmLoader(2);
        short data[][] = loader.load(new ByteArrayInputStream(bytes));
        Assert.assertEquals(1, data[0][0]);
        Assert.assertEquals(2, data[1][0]);
        Assert.assertEquals(3, data[0][1]);
        Assert.assertEquals(4, data[1][1]);
    }
}
