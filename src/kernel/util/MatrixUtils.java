package kernel.util;

import javax.swing.*;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 27.11.11
 * Time: 23:46
 */
public class MatrixUtils
{
    public static boolean equals(short[][] m1, short[][] m2)
    {
        if (m1.length != m2.length)
            return false;

        for (int i = 0; i < m1.length; i++)
        {
            if (!Arrays.equals(m1[i], m2[i]))
                return false;
        }
        return true;
    }

    public static short[][] fill(int width, int height, short value)
    {
        short[][] matrix = new short[width][height];
        for (int i = 0; i < width; i++)
            Arrays.fill(matrix[i], value);

        return matrix;
    }

    public static void writeRegion(short[][] dst, short[][] src, int x, int y, int width, int height)
    {
        for (int ix = 0; ix < width; ix++)
            System.arraycopy(src[ix], 0, dst[ix + x], y, height);
    }

    public static short[][] region(short[][] src, int x, int y, int width, int height)
    {
        short[][] region = new short[width][height];
        for (int i = x; i < x + width; i++)
            System.arraycopy(src[i], y, region[i - x], 0, height);

        return region;
    }

    public static void print(short[][] data)
    {
        for (int i=0; i<data.length; i++)
        {
            for(int j=0; j<data[i].length; j++)
                System.out.print(data[i][j]);

            System.out.println();
        }
    }
}
