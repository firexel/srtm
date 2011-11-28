package kernel.util;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 28.11.11
 * Time: 1:16
 */
public class MatrixUtilsTest
{
    @Test
    public void testWriteRegion() throws Exception
    {
        short[][] startData = MatrixUtils.fill(5, 5, (short) 0);

        short[][] dataToWrite = new short[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
                {7, 7, 7} // must skip this line
        };

        short[][] assertData = new short[][]{
                {0, 0, 0, 0, 0},
                {0, 1, 2, 3, 0},
                {0, 4, 5, 6, 0},
                {0, 7, 8, 9, 0},
                {0, 0, 0, 0, 0}
        };

        MatrixUtils.writeRegion(startData, dataToWrite, 1, 1, 3, 3);
        Assert.assertTrue(MatrixUtils.equals(startData, assertData));
    }

    @Test
    public void testRegion() throws Exception
    {
        short[][] data = new short[][]{
                {0, 0, 0, 0, 0},
                {0, 1, 2, 3, 0},
                {0, 4, 5, 6, 0},
                {0, 7, 8, 9, 0},
                {0, 0, 0, 0, 0}
        };

        short[][] assertData = new short[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        Assert.assertTrue(MatrixUtils.equals(
                assertData,
                MatrixUtils.region(data, 1, 1, 3, 3)
        ));
    }
}
