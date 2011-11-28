package kernel.source;

import junit.framework.Assert;
import kernel.util.MatrixUtils;
import kernel.util.MockSource;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 12.11.11
 * Time: 1:04
 */
public class NearestInterpolatorTest
{
    private short[][] data = new short[][]{
            {1, 10, 2, 10},
            {10, 10, 10, 10},
            {3, 10, 4, 10},
            {10, 10, 10, 10}
    };

    @Test
    public void testGet2() throws Exception
    {
        DataSource dataSource = new MockSource(data);
        NearestInterpolator interpolator = new NearestInterpolator(dataSource, 2, 2);
        short[][] data = interpolator.get(0, 0, 2, 2);
        short[][] assertData = new short[][]{
                {1, 2},
                {3, 4}
        };
        Assert.assertTrue(MatrixUtils.equals(data, assertData));
    }

    @Test
    public void testGet1() throws Exception
    {
        DataSource dataSource = new MockSource(data);
        NearestInterpolator interpolator = new NearestInterpolator(dataSource, 2, 2);
        short[][] data = interpolator.get(1, 1, 1, 1);
        short[][] assertData = new short[][]{
                {4}
        };
        Assert.assertTrue(MatrixUtils.equals(data, assertData));
    }

    @Test
    public void testGetWidth() throws Exception
    {
        NearestInterpolator interpolator = new NearestInterpolator(mock(DataSource.class), 1, 2);
        Assert.assertEquals(1, interpolator.getWidth());
    }

    @Test
    public void testGetHeight() throws Exception
    {
        NearestInterpolator interpolator = new NearestInterpolator(mock(DataSource.class), 1, 2);
        Assert.assertEquals(2, interpolator.getHeight());
    }

}
