package Control;

import IHM.ImagePanel;

import javax.swing.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * Created by Gabriel on 05/05/2015.
 */
public class ZoomMouseController implements MouseWheelListener
{
    ImagePanel panel;
    JScrollPane scroll;
    public ZoomMouseController(ImagePanel panel, JScrollPane scroll)
    {
        this.panel = panel;
        this.scroll = scroll;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        float zoom = Math.max(0, panel.getZoom() - 0.03f * e.getWheelRotation());
        panel.setZoom(zoom);
        scroll.revalidate();
        panel.repaint();
    }
}
