package kernel.source;

import junit.framework.Assert;
import kernel.util.Matrix;
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
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 3; col++)
                grid[row][col] = new MockSource((short) (row * 3 + col), 3);

        GridDataSource gridDataSource = new GridDataSource(grid, 3);
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

        Assert.assertTrue(
                gridDataSource.get(2, 2, 5, 5).equals(assertData)
        );

        Assert.assertTrue(
                gridDataSource.get(6, 0, 3, 3).equals(new Matrix(3, 3, 2))
        );

        Assert.assertTrue(
                gridDataSource.get(3, 3, 3, 3).equals(new Matrix(3, 3, 4))
        );

        Assert.assertTrue(
                gridDataSource.get(2, 3, 1, 6).equals(assertData2)
        );
    }

    @Test
    public void testGetWidthAndHeight() throws Exception
    {
        int rows = 2;
        int columns = 3;
        DataSource[][] grid = new MockSource[rows][columns];
        int edge = 7;
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[i].length; j++)
                grid[i][j] = new MockSource((short) 0, edge);

        GridDataSource gridDataSource = new GridDataSource(grid, edge);

        Assert.assertEquals(21, gridDataSource.getWidth());
        Assert.assertEquals(14, gridDataSource.getHeight());
    }
}
