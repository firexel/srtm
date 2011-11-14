package ui;

import kernel.chunk.Chunk;
import kernel.chunk.LOD;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 07.11.11
 * Time: 12:04
 */
public class LodCanvas extends JPanel
{
    private LOD lod;
    private static final int CHUNK_DISPLAY_SIZE = 1;
    private static final Color nullChunkColor = new Color(0x555555);
    private static final Color presentChunkColor = new Color(7, 97, 245);
    private static final Color loadedChunkColor = new Color(255, 201, 14);
    private boolean showLoading;

    public LodCanvas(LOD lod)
    {
        this.lod = lod;
        setDoubleBuffered(true);
        setOpaque(false); // we don't paint all our bits
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(lod.getWidth() * CHUNK_DISPLAY_SIZE, lod.getHeight() * CHUNK_DISPLAY_SIZE);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        for (int x = 0; x < lod.getWidth(); x++)
        {
            for (int y = 0; y < lod.getHeight(); y++)
            {
                Chunk chunk = lod.getChunk(x, y);
                if (chunk != null)
                {
                    if(showLoading && chunk.isLoaded())
                        g.setColor(loadedChunkColor);
                    else
                        g.setColor(presentChunkColor);
                }
                else
                {
                    g.setColor(nullChunkColor);
                }

                g.fillRect(x * CHUNK_DISPLAY_SIZE, y * CHUNK_DISPLAY_SIZE, CHUNK_DISPLAY_SIZE, CHUNK_DISPLAY_SIZE);
            }
        }
    }

    public void setLod(LOD lod)
    {
        this.lod = lod;
    }

    public void showLoading(boolean b)
    {
        this.showLoading  = b;
    }
}
