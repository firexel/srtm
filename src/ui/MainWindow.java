package ui;

import kernel.Options;
import kernel.chunk.ChunkSaver;
import kernel.chunk.ConvertedChunkIO;
import kernel.chunk.LOD;
import kernel.pool.ConvertPool;
import kernel.pool.ProgressInfo;
import kernel.pool.SavePool;
import kernel.source.DataSource;
import kernel.source.GridDataSource;
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
        ChunkSaver saver = new ConvertedChunkIO();
        SavePool savePool = new SavePool(saver, options.output, 1);
        GridDataSource gridDataSource = new GridDataSource(lod.getGrid(), lod.getChunkEdge());
        int width = options.width * options.chunkEdge;
        int height = options.height * options.chunkEdge;
        DataSource interpolator = new NearestInterpolator(gridDataSource, width, height);
        ConvertPool pool = new ConvertPool(interpolator, savePool, options.chunkEdge);
        ProgressInfo progressInfo = pool.start(2);
        createProgressViews(progressInfo);
    }

    private void createProgressViews(ProgressInfo info)
    {
        remove(optionsView);
        ProgressView progressView = new ProgressView(info);
        add(progressView);
        progressView.startUpdating();
        pack();
    }
}
