package kernel.source;

import com.sun.org.apache.bcel.internal.generic.RET;
import kernel.source.DataSource;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 12.11.11
 * Time: 0:51
 */
public class NearestInterpolator implements DataSource
{
    private DataSource source;
    private int width, height;
    private double xRatio, yRatio;

    public NearestInterpolator(DataSource source, int width, int height)
    {
        this.source = source;
        this.width = width;
        this.height = height;
        xRatio = source.getWidth() / ((double) width);
        yRatio = source.getHeight() / ((double) height);
    }

    public short get(int x, int y)
    {
        return source.get(
                clip((int) (x * xRatio), 0, source.getWidth() - 1),
                clip((int) (y * yRatio), 0, source.getHeight() - 1)
        );
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    private int clip(int val, int min, int max)
    {
        if (val < min)
            return min;
        if (val > max)
            return max;
        return val;
    }
}
