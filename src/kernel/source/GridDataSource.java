package kernel.source;

import kernel.util.Matrix;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 11.11.11
 * Time: 21:40
 */
public class GridDataSource implements DataSource
{
    private DataSource[][] grid;
    private int rows;
    private int columns;
    private int edge;

    public GridDataSource(DataSource[][] grid, int chunkEdge)
    {
        this.grid = grid;
        columns = grid[0].length;
        rows = grid.length;
        edge = chunkEdge;
    }

    public Matrix get(int x, int y, int width, int height)
    {
        Matrix result = new Matrix(width, height);
        for (int row = y / edge; row * edge < y + height; row++)
        {
            for (int col = x / edge; col * edge < x + width; col++)
            {
                int bx = Math.max(0, x - col * edge);
                int by = Math.max(0, y - row * edge);
                int bw = intersect(col * edge, edge, x, width);
                int bh = intersect(row * edge, edge, y, height);

                DataSource source = grid[row][col];
                Matrix region = source.get(bx, by, bw, bh);
                int rx = col * edge + bx - x;
                int ry = row * edge + by - y;
                result.write(region, rx, ry);
            }
        }
        return result;
    }

    private int intersect(int start1, int length1, int start2, int length2)
    {
        int maxLeft = Math.max(start1, start2);
        int minRight = Math.min(start1 + length1, start2 + length2);
        return Math.max(0, minRight - maxLeft);
    }

    public int getWidth()
    {
        return columns * edge;
    }

    public int getHeight()
    {
        return rows * edge;
    }
}
