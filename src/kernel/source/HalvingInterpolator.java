package kernel.source;

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

        if(width % 2 != 0 || height % 2 != 0)
        {
            throw new IllegalArgumentException("Source width and height must be a multiple of 2");
        }
    }

    public short get(int x, int y)
    {
        int sum = source.get(x, y) + source.get(x+1, y) + source.get(x, y+1) + source.get(x+1, y+1);
        return (short) (sum / 4);
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
