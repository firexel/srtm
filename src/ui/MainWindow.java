package ui;

import kernel.chunk.*;
import kernel.Options;
import kernel.pool.ConvertPool;
import kernel.pool.LoadPool;
import kernel.pool.SavePool;
import kernel.source.Cache;
import kernel.source.DataSource;
import kernel.source.NearestInterpolator;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 12.11.11
 * Time: 0:21
 */
public class MainWindow extends JFrame implements FolderChooser.FilePathListener, OptionsView.OnPerformConvertListener
{
    // models
    private LOD lod;

    // views
    private LodCanvas lodCanvas;
    private OptionsView optionsView;

    public MainWindow() throws HeadlessException
    {
        super("Srtm");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        add(new FolderChooser(this, "Select srtm folder"), BorderLayout.NORTH);
        pack();
        setBounds(400, 200, getWidth(), getHeight());
    }

    public void onPathSelected(FolderChooser chooser, String path)
    {
        lod = LOD.parseSrtm2(path);
        if (lodCanvas == null)
        {
            lodCanvas = new LodCanvas(lod);
            add(lodCanvas, BorderLayout.CENTER);
        }
        else
        {
            lodCanvas.setLod(lod);
            lodCanvas.repaint();
        }
        if (optionsView == null)
        {
            optionsView = new OptionsView(new Options());
            optionsView.setListener(this);
            add(optionsView, BorderLayout.SOUTH);
        }
        pack();
        repaint();
    }

    public void onPerform(Options options)
    {
        System.out.printf("Start converting to %s int format %d*%d. Chunk size: %d\n",
                options.output, options.width, options.height, options.chunkEdge
        );
        ChunkLoader loader = new DefaultSrtmLoader();
        ChunkSaver saver = new ConvertedChunkIO();
        LoadPool loadPool = new LoadPool(loader, 1, 180);
        SavePool savePool = new SavePool(saver, options.output, 1);
        Cache cache = new Cache(lod, loadPool);
        int width = options.width * options.chunkEdge;
        int height = options.height * options.chunkEdge;
        DataSource interpolator = new NearestInterpolator(cache, width, height);
        new ConvertPool(interpolator, savePool, options.chunkEdge, 4);
    }
}
