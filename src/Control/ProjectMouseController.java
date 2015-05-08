package Control;

import Model.Project;
import Model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Gabriel on 05/05/2015.
 */
public class ProjectMouseController extends MouseAdapter
{
    Project project;
    Model model;

    public ProjectMouseController(Project project, Model model)
    {
        super();
        this.project = project;
        this.model = model;
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
    public void mouseClicked(MouseEvent e)
    {
        System.out.println("Mouse Clicked");
        if (SwingUtilities.isLeftMouseButton(e))
        {
            Color chosenColor = model.getChosenColor();
            Point p = e.getPoint();
            System.out.println(p.x + ", " + p.y);
            project.drawPoint(p, chosenColor);
        }
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
    }
}
