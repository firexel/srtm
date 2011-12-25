package ui;

import kernel.Options;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 08.11.11
 * Time: 16:00
 */
public class OptionsView extends JPanel implements FolderChooser.FilePathListener, ActionListener
{
    private Options options;
    private JComboBox levelsDropDown, chunkEdgeDropDown;
    private FolderChooser outputFolderChooser;
    private static final int[] edges = new int[]{128, 256, 512};
    private static final int[][] sizes = new int[][]{
            {2048, 1024},
            {1024, 512},
            {512, 256}
    };
    private OnPerformConvertListener listener;
    private JButton startButton;

    public OptionsView(Options options)
    {
        super(new BorderLayout());
        this.options = options;

        levelsDropDown = new JComboBox();
        ItemListener levelsDropDownListener = new ItemListener()
        {
            public void itemStateChanged(ItemEvent e)
            {
                int index = levelsDropDown.getSelectedIndex();
                OptionsView.this.options.width = sizes[index][0];
                OptionsView.this.options.height = sizes[index][1];
            }
        };
        levelsDropDown.addItemListener(levelsDropDownListener);
        for (int i = 0; i < sizes.length; i++)
            levelsDropDown.addItem(String.format("%d×%d", sizes[i][0], sizes[i][1]));
        levelsDropDown.setSelectedIndex(1);

        chunkEdgeDropDown = new JComboBox();
        ItemListener chunkEdgeDropDownListener = new ItemListener()
        {
            public void itemStateChanged(ItemEvent e)
            {
                OptionsView.this.options.chunkEdge = edges[chunkEdgeDropDown.getSelectedIndex()];
            }
        };
        chunkEdgeDropDown.addItemListener(chunkEdgeDropDownListener);
        for (int i = 0; i < edges.length; i++)
        {
            chunkEdgeDropDown.addItem(String.format("%d×%d", edges[i], edges[i]));
            if (options.chunkEdge == edges[i])
                chunkEdgeDropDown.setSelectedIndex(i);
        }

        outputFolderChooser = new FolderChooser(this, "Select output folder");

        add(outputFolderChooser, BorderLayout.NORTH);

        startButton = new JButton("Convert");
        startButton.addActionListener(this);
        startButton.setEnabled(false);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Levels:"));
        panel.add(levelsDropDown);
        panel.add(new JLabel("Chunk size:"));
        panel.add(chunkEdgeDropDown);
        panel.add(startButton);

        add(panel, BorderLayout.SOUTH);
    }

    public void setListener(OnPerformConvertListener listener)
    {
        this.listener = listener;
    }

    public void onPathSelected(FolderChooser chooser, String path)
    {
        options.output = path;
        startButton.setEnabled(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == startButton)
        {
            if (listener != null)
                listener.onPerform(options);
        }
    }

    public interface OnPerformConvertListener
    {
        void onPerform(Options options);
    }
}
