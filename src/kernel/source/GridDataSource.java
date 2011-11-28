package kernel.source;

import kernel.util.MatrixUtils;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 11.11.11
 * Time: 21:40
 */
public class GridDataSource implements DataSource
{
    private DataSource[][] grid;
    private int height;
    private int width;
    private int edge;

    public GridDataSource(DataSource[][] grid)
    {
        this.grid = grid;
        height = grid[0].length;
        width = grid.length;
        edge = grid[0][0].getWidth();
    }

    public short[][] get(int x, int y, int width, int height)
    {
        short[][] result = new short[height][width];
        for (int cy = x / edge; cy * edge < x + width; cy++)
        {
            for (int cx = y / edge; cx * edge < y + height; cx++)
            {
                int bx = Math.max(0, y - cx * edge);
                int by = Math.max(0, x - cy * edge);
                int bw = intersect(cx * edge, edge, y, height);
                int bh = intersect(cy * edge, edge, x, width);

                DataSource source = grid[cx][cy];
                short[][] data = source.get(bx, by, bw, bh);
                int rx = cx * edge + bx - y;
                int ry = cy * edge + by - x;
                MatrixUtils.writeRegion(
                        result,
                        data,
                        rx, ry,
                        bw, bh
                );
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
        return width * edge;
    }

    public int getHeight()
    {
        return height * edge;
    }
}
