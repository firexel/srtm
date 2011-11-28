package kernel.source;

import javax.xml.crypto.dsig.spec.XPathFilter2ParameterSpec;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 12.11.11
 * Time: 21:33
 */
public class HalvingInterpolator implements DataSource
{
    private DataSource source;
    private int width, height;

    public HalvingInterpolator(DataSource source)
    {
        this.source = source;
        width = source.getWidth();
        height = source.getHeight();

        if (width % 2 != 0 || height % 2 != 0)
        {
            throw new IllegalArgumentException("Source width and height must be a multiple of 2");
        }
    }

    public short[][] get(int x, int y, int width, int height)
    {
        if (x + (width - 1) > getWidth())
            throw new IllegalArgumentException(String.format("Too large width (x=%d, width=%d)", x, width));

        if (y + (height - 1) > getHeight())
            throw new IllegalArgumentException(String.format("Too large height (y=%d, height=%d)", y, height));

        short[][] result = new short[width][height];
        short[][] data = source.get(x * 2, y * 2, width * 2, height * 2);
        for (int ix = x; ix < width; ix++)
        {
            for (int iy = y; iy < height; iy++)
            {
                int sum = data[ix*2][iy*2]
                        + data[ix*2 + 1][iy*2]
                        + data[ix*2][iy*2 + 1]
                        + data[ix*2+1][iy*2+1];

                result[ix][iy] = (short) (sum / 4);
            }
        }
        return result;
    }

    public int getWidth()
    {
        return width / 2;
    }

    public int getHeight()
    {
        return height / 2;
    }
}
