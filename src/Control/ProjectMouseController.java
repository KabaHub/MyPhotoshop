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
        if (e != null)
        {
            if (e.isControlDown())
            {
                float zoom = (e.getWheelRotation() < 0) ? 0.05f : -0.05f;
                project.setZoom(zoom);
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        if (project.getImagePanel().previewPencil.isEmpty())
        {
            project.getImagePanel().pencilSize = model.getPencilSize();
            project.getImagePanel().pencilColor = model.getChosenColor();
        }
        points.add(e.getPoint());
        project.addToPencilPreview(e.getPoint());
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        project.getImagePanel().pencilSize = model.getPencilSize();
        project.getImagePanel().pencilColor = model.getChosenColor();

        project.drawPencil();
        points.clear();
        project.clearPencilPreview();
    }
}
