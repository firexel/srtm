package ui;

import kernel.pool.ProgressInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 19.12.11
 * Time: 8:26
 */
public class ProgressView extends JPanel
{
    // model
    private ProgressInfo info;

    // view
    private JLabel chunksInfo;
    private JProgressBar progressBar;

    // util
    private Timer timer;
    private ActionListener actionListener = new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            updateParams();
            repaint();
            timer = new Timer(1000, actionListener);
            timer.start();
        }
    };

    public ProgressView(ProgressInfo info)
    {
        super(new BorderLayout());
        this.info = info;

        progressBar = new JProgressBar(0, 100);
        chunksInfo = new JLabel();

        add(chunksInfo, BorderLayout.EAST);
        add(progressBar, BorderLayout.CENTER);
    }

    private void updateParams()
    {
        progressBar.setValue((int) (((float) info.getChunksCovered()) / info.getChunksTotal() * 100));
        chunksInfo.setText(String.format("%d / %d proceeded. %d new chunks saved",
                info.getChunksCovered(),
                info.getChunksTotal(),
                info.getChunksSaved()
        ));
    }

    public void startUpdating()
    {
        timer = new Timer(1000, actionListener);
        timer.start();
    }
}
