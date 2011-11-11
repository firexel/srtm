package ui;

import kernel.Options;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.nio.channels.NonWritableChannelException;

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

    public OptionsView(Options options)
    {
        super(new BorderLayout());
        this.options = options;

        levelsDropDown = new JComboBox();
        ItemListener levelsDropDownListener = new ItemListener()
        {
            public void itemStateChanged(ItemEvent e)
            {
                OptionsView.this.options.levels = Integer.parseInt(e.getItem().toString());
            }
        };
        levelsDropDown.addItemListener(levelsDropDownListener);
        for (int i = 1; i <= options.maxLevel; i++)
            levelsDropDown.addItem("" + i);
        levelsDropDown.setSelectedIndex(options.maxLevel - 1);

        chunkEdgeDropDown = new JComboBox();
        ItemListener chunkEdgeDropDownListener = new ItemListener()
        {
            public void itemStateChanged(ItemEvent e)
            {
                OptionsView.this.options.chunkEdge = edges[chunkEdgeDropDown.getSelectedIndex()];
            }
        };
        chunkEdgeDropDown.addItemListener(chunkEdgeDropDownListener);
        for(int i=0; i<edges.length; i++)
        {
            chunkEdgeDropDown.addItem(edges[i] + "×" + edges[i]);
            if(options.chunkEdge == edges[i])
                chunkEdgeDropDown.setSelectedIndex(i);
        }

        outputFolderChooser = new FolderChooser(this, "Select output folder");

        add(outputFolderChooser, BorderLayout.NORTH);

        JButton startButton = new JButton("Convert");
        startButton.addActionListener(this);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Levels:"));
        panel.add(levelsDropDown);
        panel.add(new JLabel("Chunk size:"));
        panel.add(chunkEdgeDropDown);
        panel.add(startButton);

        add(panel, BorderLayout.SOUTH);
    }

    public void onPathSelected(FolderChooser chooser, String path)
    {
        options.output = path;
    }

    public void actionPerformed(ActionEvent e)
    {

    }
}
