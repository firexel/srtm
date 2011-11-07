package ui;

import kernel.Chunk;
import kernel.Srtm;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 07.11.11
 * Time: 12:04
 */
public class SrtmCanvas extends Canvas
{
    private Srtm.LOD lod;
    private static final int CHUNK_DISPLAY_SIZE = 2;
    private static final Color nullChunkColor = new Color(0x888888);
    private static final Color presentChunkColor = new Color(7, 97, 245);

    public SrtmCanvas(Srtm.LOD lod)
    {
        this.lod = lod;
    }

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(lod.getWidth() * CHUNK_DISPLAY_SIZE, lod.getHeight() * CHUNK_DISPLAY_SIZE);
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);

        for (int x = 0; x < lod.getWidth(); x++)
        {
            for (int y = 0; y < lod.getHeight(); y++)
            {
                Chunk chunk = lod.getChunk(x, y);
                if (chunk != null)
                    g.setColor(presentChunkColor);
                else
                    g.setColor(nullChunkColor);

                g.fillRect(x * CHUNK_DISPLAY_SIZE, y * CHUNK_DISPLAY_SIZE, CHUNK_DISPLAY_SIZE, CHUNK_DISPLAY_SIZE);
            }
        }
    }
}
