import kernel.*;
import ui.FolderChooser;
import ui.OptionsView;
import ui.LodCanvas;

import javax.swing.*;
import java.awt.*;
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
    private static LoadPool pool;
    private static LodCanvas lodCanvas;
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
                LOD lod = LOD.parseSrtm2(path);
                lodCanvas = new LodCanvas(lod);
                frame.add(lodCanvas, BorderLayout.CENTER);
                optionsView = new OptionsView(new Options());
                frame.add(optionsView, BorderLayout.SOUTH);
                frame.pack();
                frame.repaint();
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