package Control;

import Model.Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Gabriel on 05/05/2015.
 */
public class ProjectMouseController extends MouseAdapter
{
    Project project;

    public ProjectMouseController(Project project)
    {
        this.project = project;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        if (e.isControlDown())
        {
//        float zoom = Math.max(0, project.getZoom() - 0.03f * e.getWheelRotation());
            float zoom = (e.getWheelRotation() < 0) ? 0.05f : -0.05f;
            project.setZoom(zoom);
        }
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        if (SwingUtilities.isLeftMouseButton(e))
        {
            Point p = e.getPoint();
        }
    }
}
