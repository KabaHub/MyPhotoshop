package Model;

import IHM.ImagePanel;
import plugin.IPlugin;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Gabriel on 24/04/2015.
 */
public class ImageState
{
    private Project project;
    private IPlugin appliedIPlugin;
    private ImagePanel imagePanel;
    Date date;

    public ImageState(Project project, IPlugin appliedIPlugin, ImagePanel imagePanel)
    {
        this.project = project;
        this.appliedIPlugin = appliedIPlugin;
        this.imagePanel = imagePanel;
        date = Calendar.getInstance().getTime();
    }
}
