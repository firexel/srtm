package kernel;

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
        return source.get((int) (x * xRatio), (int) (y * yRatio));
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public boolean isAvailable(int x, int y)
    {
        return source.isAvailable((int) (x * xRatio), (int) (y * yRatio));
    }
}
