package Control;

import Model.Project;
import Model.Model;
import Model.ToolType;

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

    private ArrayList<Point> movePoints = new ArrayList<>();

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
        if (model.getCurrentTool() == ToolType.MOVE_LAYER_TOOL)
        {
            movePoints.add(e.getPoint());
            project.getImagePanel().moveCurrentLayer(movePoints);
        } else
        {
            if (project.getImagePanel().previewPencil.isEmpty())
            {
                project.getImagePanel().toolType = model.getCurrentTool() != ToolType.NONE ? model.getCurrentTool() : ToolType.BRUSH_TOOL;
                project.getImagePanel().pencilSize = model.getPencilSize();
                project.getImagePanel().pencilType = model.getPencilType();
                project.getImagePanel().pencilColor = model.getChosenColor();
            }
            project.addToPencilPreview(e.getPoint());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        if (model.getCurrentTool() == ToolType.MOVE_LAYER_TOOL)
        {
            movePoints.clear();
        } else
        {
            project.drawPencil();
            project.clearPencilPreview();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        if (model.getCurrentTool() == ToolType.EYEDROPPER_TOOL)
        {
            Color pixelColor = project.getImagePanel().getPixelColor(e.getPoint());
            model.setChosenColor(pixelColor);
            model.forceColorUpdate(pixelColor);
        }
    }
}
