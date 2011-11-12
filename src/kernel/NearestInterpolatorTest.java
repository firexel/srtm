package kernel;

import junit.framework.Assert;
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
    @Test
    public void testGet() throws Exception
    {
        DataSource dataSource = mock(DataSource.class);
        NearestInterpolator interpolator = new NearestInterpolator(dataSource, 2, 2);
        for(int x=0; x<2; x++)
            for(int y=0; y<2; y++)
                interpolator.get(x, y);

        verify(dataSource, times(4)).get(anyInt(), anyInt());
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
