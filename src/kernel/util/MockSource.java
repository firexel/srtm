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
    private Matrix data;
    private int width, height;

    public MockSource(short val, int edge)
    {
        width = height = edge;
        data = new Matrix(edge, edge, val);
    }

    public MockSource(short[][] data)
    {
        height = data.length;
        width = data[0].length;
        this.data = new Matrix(width, height, data);
    }

    public Matrix get(int bx, int by, int bw, int bh)
    {
        return data.region(bx, by, bw, bh);
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
