package kernel.source;

import kernel.util.Matrix;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 11.11.11
 * Time: 21:40
 */
public interface DataSource
{
    Matrix get(int x, int y, int width, int height);
    int getWidth();
    int getHeight();
}
