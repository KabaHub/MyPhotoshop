package Model;

import IHM.ImagePanel;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.nio.Buffer;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Gabriel on 24/04/2015.
 */
public class ImageState implements Serializable
{
    private Project project;
    private String appliedIPlugin;
    private BufferedImage image;
    Date date;

    public ImageState(Project project, String appliedIPlugin, BufferedImage image)
    {
        this.project = project;
        this.appliedIPlugin = appliedIPlugin;
        this.image = image;
        date = Calendar.getInstance().getTime();
    }

    public String getAppliedIPlugin()
    {
        return appliedIPlugin;
    }
}
