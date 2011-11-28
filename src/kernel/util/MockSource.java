package kernel.util;

import kernel.source.DataSource;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 27.11.11
 * Time: 23:46
 */
public class MockSource implements DataSource
{
    private short data[][];
    private int width, height;

    public MockSource(short val, int edge)
    {
        width = height = edge;
        data = MatrixUtils.fill(edge, edge, val);
    }

    public MockSource(short[][] data)
    {
        this.data = data;
        width = data.length;
        height = data[0].length;
    }

    public short[][] get(int bx, int by, int bw, int bh)
    {
        return MatrixUtils.region(data, bx, by, bw, bh);
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }
}
