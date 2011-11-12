package ui;

import kernel.chunk.LOD;
import kernel.Options;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 12.11.11
 * Time: 0:21
 */
public class MainWindow extends JFrame implements FolderChooser.FilePathListener
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
            add(optionsView, BorderLayout.SOUTH);
        }
        pack();
        repaint();
    }
}
