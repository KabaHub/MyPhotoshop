package Control;

import Model.Project;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * Created by Gabriel on 05/05/2015.
 */
public class ProjectMouseController implements MouseWheelListener
{
    Project project;

    public ProjectMouseController(Project project)
    {
        this.project = project;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e)
    {
//        float zoom = Math.max(0, project.getZoom() - 0.03f * e.getWheelRotation());
        float zoom = (e.getWheelRotation() < 0) ? 0.03f : -0.03f;
        System.out.println(zoom);
        project.setZoom(zoom);
    }
}
