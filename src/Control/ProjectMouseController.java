package Control;

import Model.Project;
import Model.Model;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by Gabriel on 05/05/2015.
 */
public class ProjectMouseController extends MouseAdapter
{
    private Project project;
    private Model model;

    private ArrayList<Point> points = new ArrayList<>();

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
    public void mouseDragged(MouseEvent e)
    {
        points.add(e.getPoint());
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
    }

    @Override
    public void mousePressed(MouseEvent e)
    {

    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        int pencilSize = model.getPencilSize();
        Color color = model.getChosenColor();
        project.drawPencil(points, pencilSize, color);
        points.clear();
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
