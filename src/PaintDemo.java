import java.awt.*;
import java.awt.event.*;

/*
 ***************************************************************
 * Silly Sample program which demonstrates the basic paint
 * callback mechanism in the AWT.
 ***************************************************************
 */
public class PaintDemo
{
    public static void main(String[] args)
    {
        Frame f = new Frame("Have a nice day!");
        f.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        f.add(new SmileyCanvas(Color.yellow), BorderLayout.CENTER);
        f.pack();
        f.show();
    }
}

/**
 * A canvas which renders a smiley-face to the screen
 * <p/>
 * Note: Canvas is a heavyweight superclass, which makes
 * SmileyCanvas also heavyweight.  To convert this class to
 * a lightweight, change "extends Canvas" to "extends Component".
 */
class SmileyCanvas extends Canvas
{

    public SmileyCanvas(Color faceColor)
    {
        setForeground(faceColor);
    }

    public Dimension getPreferredSize()
    {
        return new Dimension(300, 300);
    }

    /*
    * Paint when the AWT tells us to...
    */
    public void paint(Graphics g)
    {
        // Dynamically calculate size information
        // (the canvas may have been resized externally...)
        Dimension size = getSize();
        int d = Math.min(size.width, size.height); // diameter
        int ed = d / 20; // eye diameter
        int x = (size.width - d) / 2;
        int y = (size.height - d) / 2;

        // draw head (color already set to foreground)
        g.fillOval(x, y, d, d);
        g.setColor(Color.black);
        g.drawOval(x, y, d, d);

        // draw eyes
        g.fillOval(x + d / 3 - (ed / 2), y + d / 3 - (ed / 2), ed, ed);
        g.fillOval(x + (2 * (d / 3)) - (ed / 2), y + d / 3 - (ed / 2), ed, ed);

        //draw mouth
        g.drawArc(x + d / 4, y + 2 * (d / 5), d / 2, d / 3, 0, -180);
    }
}

