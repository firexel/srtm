package kernel.source;

import junit.framework.Assert;
import kernel.util.MatrixUtils;
import kernel.util.MockSource;
import org.junit.Test;

import java.nio.channels.NonWritableChannelException;

import static org.mockito.Mockito.*;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 12.11.11
 * Time: 21:39
 */
public class HalvingInterpolatorTest
{
    private short[][] data = new short[][]{
            {1, 1, 2, 2},
            {1, 1, 2, 2},
            {3, 3, 4, 4},
            {3, 3, 4, 4}
    };

    private short[][] assertResult = new short[][]{
            {1, 2},
            {3, 4}
    };

    @Test
    public void testGet() throws Exception
    {
        DataSource source = new MockSource(data);
        HalvingInterpolator interpolator = new HalvingInterpolator(source);
        Assert.assertTrue(MatrixUtils.equals(assertResult, interpolator.get(0, 0, 2, 2)));
    }

    @Test
    public void testGetWidth() throws Exception
    {
        DataSource source = mock(DataSource.class);
        when(source.getWidth()).thenReturn(10);
        HalvingInterpolator interpolator = new HalvingInterpolator(source);
        Assert.assertEquals(5, interpolator.getWidth());
    }

    @Test
    public void testGetHeight() throws Exception
    {
        DataSource source = mock(DataSource.class);
        when(source.getHeight()).thenReturn(20);
        HalvingInterpolator interpolator = new HalvingInterpolator(source);
        Assert.assertEquals(10, interpolator.getHeight());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSizeException()
    {
        DataSource source = mock(DataSource.class);
        when(source.getHeight()).thenReturn(11);
        HalvingInterpolator interpolator = new HalvingInterpolator(source);
    }
}
