package kernel.source;

import kernel.util.Matrix;

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
        xRatio = (source.getWidth() / ((double) width));
        yRatio = (source.getHeight() / ((double) height));
    }

    public Matrix get(int x, int y, int width, int height)
    {
        if (x + (width - 1) > getWidth())
            throw new IllegalArgumentException(String.format("Too large width (x=%d, width=%d)", x, width));

        if (y + (height - 1) > getHeight())
            throw new IllegalArgumentException(String.format("Too large height (y=%d, height=%d)", y, height));

        int nx = clip((int) (x * xRatio), 0, source.getWidth() - 1);
        int ny = clip((int) (y * yRatio), 0, source.getHeight() - 1);
        int nw = clip((int) (width * xRatio), 0, source.getWidth() - nx);
        int nh = clip((int) (height * yRatio), 0, source.getHeight() - ny);
        
        Matrix src = source.get(nx, ny, nw, nh);
        Matrix result = new Matrix(width, height);
        for (int ix = 0; ix < width; ix++)
        {
            for (int iy = 0; iy < height; iy++)
            {
                int sx = clip((int) (ix * xRatio), 0, nw);
                int sy = clip((int) (iy * yRatio), 0, nh);
                result.set(ix, iy, src.get(sx, sy));
            }
        }
        return result;
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
