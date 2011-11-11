package ui;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 08.11.11
 * Time: 15:01
 */
public class FolderChooser extends JPanel implements ActionListener
{
    @NotNull private FilePathListener listener;
    private JFileChooser fileChooser;
    private JButton openButton;
    private JLabel label;

    public FolderChooser(@NotNull FilePathListener listener, String caption)
    {
        super(new BorderLayout());
        this.listener = listener;

        fileChooser = new JFileChooser(System.getProperty("user.dir"));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        openButton = new JButton("...");
        openButton.addActionListener(this);

        label = new JLabel(caption);

        JPanel row = new JPanel();
        row.add(label, FlowLayout.LEFT);
        row.add(openButton);

        add(row);
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == openButton)
        {
            int returnVal = fileChooser.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                File file = fileChooser.getSelectedFile();
                listener.onPathSelected(this, file.getAbsolutePath());
                label.setText(file.getAbsolutePath());
            }
        }
    }

    public interface FilePathListener
    {
        void onPathSelected(FolderChooser chooser, String path);
    }
}
