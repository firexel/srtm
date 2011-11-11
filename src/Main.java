import kernel.Chunk;
import kernel.LoadPool;
import kernel.Options;
import kernel.Srtm;
import ui.FolderChooser;
import ui.OptionsView;
import ui.SrtmCanvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 26.10.11
 * Time: 5:27
 */
public class Main
{
    private static final int MAX_CHUNKS = 200;
    private static Srtm srtm;
    private static LoadPool pool;
    private static SrtmCanvas srtmCanvas;
    private static OptionsView optionsView;

    public static void main(String argv[])
    {
        final JFrame frame = new JFrame("Srtm");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        FolderChooser.FilePathListener filePathListener = new FolderChooser.FilePathListener()
        {
            public void onPathSelected(FolderChooser chooser, String path)
            {
                srtm = new Srtm(path);
                srtmCanvas = new SrtmCanvas(srtm.getTopLod());
                frame.add(srtmCanvas, BorderLayout.CENTER);
                optionsView = new OptionsView(new Options());
                frame.add(optionsView, BorderLayout.SOUTH);
                frame.pack();
                frame.repaint();

                Srtm.LOD lod = srtm.getTopLod();
                for (int x = 0; x < lod.getWidth(); x++)
                {
                    for (int y = 0; y < lod.getHeight(); y++)
                    {
                        Chunk lodChunk = lod.getChunk(x, y);
                        if (lodChunk != null)
                        {
                            pool.enqueue(lodChunk);
                        }
                    }
                }
            }
        };
        frame.add(new FolderChooser(filePathListener, "Select srtm folder"), BorderLayout.NORTH);
        frame.pack();
        frame.setBounds(400, 200, frame.getWidth(), frame.getHeight());
        frame.setVisible(true);

        final Queue<Chunk> chunks = new LinkedList<Chunk>();

        pool = new LoadPool(1);
        pool.addChangeListener(new LoadPool.LoadListener()
        {
            public void onChunkLoaded(Chunk chunk)
            {
                chunks.add(chunk);
                if (chunks.size() > MAX_CHUNKS)
                    chunks.remove().unload();

                frame.repaint();
            }
        });


    }
}