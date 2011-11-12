package kernel;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 11.11.11
 * Time: 21:40
 */
public interface DataSource
{
    short get(int x, int y);
    int getWidth();
    int getHeight();
}
