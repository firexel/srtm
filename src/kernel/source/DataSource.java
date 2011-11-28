package kernel.source;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 11.11.11
 * Time: 21:40
 */
public interface DataSource
{
    short[][] get(int x, int y, int width, int height);
    int getWidth();
    int getHeight();
}
