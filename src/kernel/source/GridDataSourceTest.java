package kernel.source;

import junit.framework.Assert;
import kernel.util.MatrixUtils;
import kernel.util.MockSource;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 14.11.11
 * Time: 2:24
 */
public class GridDataSourceTest
{
    @Test
    public void testGet() throws Exception
    {
        DataSource[][] grid = new DataSource[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                grid[i][j] = new MockSource((short) (i * 3 + j), 3);

        GridDataSource gridDataSource = new GridDataSource(grid);
        short[][] assertData = new short[][]{
                {0, 1, 1, 1, 2},
                {3, 4, 4, 4, 5},
                {3, 4, 4, 4, 5},
                {3, 4, 4, 4, 5},
                {6, 7, 7, 7, 8}
        };

        short[][] assertData2 = new short[][]{
                {3},
                {3},
                {3},
                {6},
                {6},
                {6}
        };
        Assert.assertTrue(MatrixUtils.equals(
                assertData,
                gridDataSource.get(2, 2, 5, 5)
        ));
        Assert.assertTrue(MatrixUtils.equals(
                MatrixUtils.fill(3, 3, (short) 2),
                gridDataSource.get(6, 0, 3, 3)
        ));
        Assert.assertTrue(MatrixUtils.equals(
                MatrixUtils.fill(3, 3, (short) 4),
                gridDataSource.get(3, 3, 3, 3)
        ));
        Assert.assertTrue(MatrixUtils.equals(
                assertData2,
                gridDataSource.get(2, 3, 1, 6)
        ));
    }

    @Test
    public void testGetWidthAndHeight() throws Exception
    {
        DataSource[][] grid = new MockSource[2][3];
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[i].length; j++)
                grid[i][j] = new MockSource((short) 0, 7);

        GridDataSource gridDataSource = new GridDataSource(grid);

        Assert.assertEquals(14, gridDataSource.getWidth());
        Assert.assertEquals(21, gridDataSource.getHeight());
    }
}
