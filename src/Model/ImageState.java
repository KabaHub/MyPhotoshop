package Model;


import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Gabriel on 24/04/2015.
 */
public class ImageState implements Serializable
{
    private String appliedIPlugin;
    private transient BufferedImage image;
    Date date;

    public ImageState(String appliedIPlugin, BufferedImage image)
    {
        this.appliedIPlugin = appliedIPlugin;
        this.image = image;
        date = Calendar.getInstance().getTime();
    }

    public String getAppliedIPlugin()
    {
        return appliedIPlugin;
    }
}
