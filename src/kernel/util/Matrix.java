package kernel.util;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 10.12.11
 * Time: 10:11
 */
public class Matrix
{
    private short matrix[][];
    private int width, height;

    public Matrix(int width, int height)
    {
        matrix = new short[height][width];
        this.width = width;
        this.height = height;
    }

    public Matrix(int width, int height, int filler)
    {
        this(width, height);
        fill((short) filler);
    }

    public Matrix(int width, int height, short[][] matrix)
    {
        this.width = width;
        this.height = height;
        this.matrix = matrix;

        if (matrix.length != height)
            throw new IllegalArgumentException(String.format(
                    "Given matrix.length (%d) don't equal to given height (%d)",
                    matrix.length, height
            ));

        for (int row = 0; row < height; row++)
            if (width != matrix[row].length)
                throw new IllegalArgumentException(String.format(
                        "Given matrix row length (%d) don't equal to given width (%d) at row %d",
                        matrix[row].length, width, row
                ));
    }

    public Matrix fill(short filler)
    {
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++)
                matrix[row][col] = filler;

        return this;
    }

    public short get(int x, int y)
    {
        checkBounds(x, y);
        return matrix[y][x];
    }

    private void checkBounds(int x, int y)
    {
        if (x < 0 || x >= width)
            throw new ArrayIndexOutOfBoundsException("Requested index " + x + " when width is " + width);

        if (y < 0 || y >= height)
            throw new ArrayIndexOutOfBoundsException("Requested index " + y + " when height is " + height);
    }

    public void set(int x, int y, int value)
    {
        checkBounds(x, y);
        matrix[y][x] = (short) value;
    }

    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        for (int row = 0; row < height; row++)
        {
            for (int col = 0; col < width; col++)
            {
                builder.append(matrix[row][col]);
                builder.append(' ');
            }
            builder.append('\n');
        }
        return builder.toString();
    }

    public Matrix region(int x, int y, int width, int height)
    {
        checkBounds(x, y);
        checkBounds(x + width - 1, y + height - 1);

        Matrix region = new Matrix(width, height);
        for (int row = 0; row < height; row++)
            System.arraycopy(matrix[row + y], x, region.matrix[row], 0, width);

        return region;
    }

    public Matrix write(Matrix region, int x, int y)
    {
        checkBounds(x, y);
        checkBounds(x + region.width - 1, y + region.height - 1);

        for (int row = 0; row < region.height; row++)
            System.arraycopy(region.matrix[row], 0, matrix[row + y], x, region.width);

        return this;
    }

    public boolean isEmpty()
    {
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++)
                if (matrix[row][col] != 0)
                    return false;

        return true;
    }
    
    public boolean equals(short[][] comp)
    {
        if (comp.length != matrix.length)
            return false;

        for (int row = 0; row < height; row++)
        {
            if (matrix[row].length != comp[row].length)
                return false;

            for (int col = 0; col < width; col++)
                if (matrix[row][col] != comp[row][col])
                    return false;
        }

        return true;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == this)
        {
            return true;
        }
        else if (obj instanceof Matrix)
        {
            Matrix m = (Matrix) obj;
            return m.width == width && m.height == height && equals(m.matrix);
        }
        else
        {
            return super.equals(obj);
        }
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
