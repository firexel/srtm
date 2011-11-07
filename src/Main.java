import kernel.Chunk;
import kernel.Srtm;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 26.10.11
 * Time: 5:27
 */
public class Main
{
    public static void main(String argv[])
    {
        Srtm srtm = new Srtm("C:\\Users\\alex\\Downloads\\SRTM3\\chunks");


        //List<Chunk> chunks = srtm.enumerate();
        //float percent = (chunks.size() / (360f * 180f)) * 100f;
        System.out.printf("%d chunks total, %f%% of earth area", chunks.size(), percent);
    }
}