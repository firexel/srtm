package kernel.source;

import junit.framework.Assert;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 12.11.11
 * Time: 21:39
 */
public class HalvingInterpolatorTest
{
    @Test
    public void testGet() throws Exception
    {
        DataSource source = mock(DataSource.class);
        when(source.get(0, 0)).thenReturn((short) 10);
        when(source.get(1, 0)).thenReturn((short) 1);
        when(source.get(0, 1)).thenReturn((short) 2);
        when(source.get(1, 1)).thenReturn((short) 3);
        when(source.getWidth()).thenReturn(2);
        when(source.getHeight()).thenReturn(2);

        HalvingInterpolator interpolator = new HalvingInterpolator(source);
        Assert.assertEquals(4, interpolator.get(0, 0)); // (10 + 1 + 2 + 3) / 4 = 16 / 4 = 4
        verify(source, times(4)).get(anyInt(), anyInt());
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
