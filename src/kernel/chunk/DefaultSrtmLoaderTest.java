package kernel.chunk;

import junit.framework.Assert;
import kernel.util.MatrixUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;

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
        FileOutputStream stream = new FileOutputStream("DefaultSrtmLoaderTest");
        DataOutputStream dataOutputStream = new DataOutputStream(stream);
        short[] fileData = new short[]{
                0, 0, 0, 1, 1, 1, 2, 2, 2,
                0, 0, 0, 1, 1, 1, 2, 2, 2,
                0, 0, 0, 1, 1, 1, 2, 2, 2,
                3, 3, 3, 4, 4, 4, 5, 5, 5,
                3, 3, 3, 4, 4, 4, 5, 5, 5,
                3, 3, 3, 4, 4, 4, 5, 5, 5,
                6, 6, 6, 7, 7, 7, 8, 8, 8,
                6, 6, 6, 7, 7, 7, 8, 8, 8,
                6, 6, 6, 7, 7, 7, 8, 8, 8
        };
        
        for (short aFileData : fileData)
            dataOutputStream.writeShort(aFileData);

        dataOutputStream.flush();
        dataOutputStream.close();
        stream.flush();
        stream.close();

        RandomAccessFile file = new RandomAccessFile("DefaultSrtmLoaderTest", "r");
        DefaultSrtmLoader loader = new DefaultSrtmLoader(9);
        short[][] data = loader.load(file, 2, 2, 5, 5);
        short[][] assertData = new short[][]{
                {0, 1, 1, 1, 2},
                {3, 4, 4, 4, 5},
                {3, 4, 4, 4, 5},
                {3, 4, 4, 4, 5},
                {6, 7, 7, 7, 8}
        };
        Assert.assertTrue(MatrixUtils.equals(
                assertData,
                data
        ));
    }
}
