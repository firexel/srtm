package kernel.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 19.12.11
 * Time: 10:52
 */
public class MatrixTest
{
    @Test
    public void testFill() throws Exception
    {
        Matrix matrix = new Matrix(2, 3);
        matrix.fill((short) 4);
        Assert.assertTrue(matrix.equals(new short[][]{
                {4, 4},
                {4, 4},
                {4, 4}
        }));
    }

    @Test
    public void testGet() throws Exception
    {
        Matrix matrix = new Matrix(2, 3, new short[][]{
                {1, 2},
                {3, 4},
                {5, 6}
        });
        Assert.assertEquals(1, matrix.get(0, 0));
        Assert.assertEquals(2, matrix.get(1, 0));
        Assert.assertEquals(6, matrix.get(1, 2));
    }

    @Test
    public void testSet() throws Exception
    {
        Matrix matrix = new Matrix(2, 3);
        matrix.set(0, 0, 1);
        matrix.set(1, 1, 2);
        Assert.assertTrue(matrix.equals(new short[][]{
                {1, 0},
                {0, 2},
                {0, 0}
        }));
    }

    @Test
    public void testToString() throws Exception
    {
        Matrix matrix = new Matrix(2, 3, new short[][]{
                {1, 2},
                {3, 4},
                {5, 6}
        });
        String string = "1 2 \n3 4 \n5 6 \n";
        Assert.assertEquals(string, matrix.toString());
    }

    @Test
    public void testRegion() throws Exception
    {
        Matrix matrix = new Matrix(5, 4, new short[][]{
                {1, 2, 3, 4, 5},
                {6, 7, 8, 9, 0},
                {0, 9, 8, 7, 6},
                {5, 4, 3, 2, 1}
        });
        Matrix region = matrix.region(2, 1, 3, 3);
        Assert.assertTrue(region.equals(new short[][]{
                {8, 9, 0},
                {8, 7, 6},
                {3, 2, 1}
        }));
    }

    @Test
    public void testWrite() throws Exception
    {
        Matrix matrix = new Matrix(5, 4);
        Matrix region = new Matrix(3, 2, new short[][]{
                {1, 2, 3},
                {4, 5, 6}
        });
        matrix.write(region, 1, 0);
        Assert.assertTrue(matrix.equals(new short[][]{
                {0, 1, 2, 3, 0},
                {0, 4, 5, 6, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0}
        }));
    }

    @Test
    public void testIsEmpty() throws Exception
    {
        Matrix emptyMatrix = new Matrix(2, 3);
        Matrix notEmptyMatrix = new Matrix(3, 2, 1);
        Assert.assertTrue(emptyMatrix.isEmpty());
        Assert.assertFalse(notEmptyMatrix.isEmpty());
    }

    @Test
    public void testEquals() throws Exception
    {
        Matrix region1 = new Matrix(3, 2, new short[][]{
                {1, 2, 3},
                {4, 5, 6}
        });

        Matrix region2 = new Matrix(3, 2, new short[][]{
                {1, 2, 3},
                {4, 0, 6}
        });

        Matrix region3 = new Matrix(3, 2, new short[][]{
                {1, 2, 3},
                {4, 0, 6}
        });

        Assert.assertFalse(region1.equals(region2));
        Assert.assertFalse(region2.equals(region1));

        Assert.assertTrue(region2.equals(region3));
        Assert.assertTrue(region3.equals(region2));
    }
}
