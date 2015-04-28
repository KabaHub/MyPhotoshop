package Model;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Gabriel on 24/04/2015.
 */
public class ImageState implements Serializable
{
    private String appliedIPlugin;
    private transient BufferedImage image;
    private byte[] imageByte;
    Date date;

    public ImageState(String appliedIPlugin, BufferedImage image)
    {
        this.appliedIPlugin = appliedIPlugin;
        this.image = image;
        date = Calendar.getInstance().getTime();
        prepareToSerialization();
    }

    public void buildImage()
    {
        InputStream in = new ByteArrayInputStream(imageByte);
        try
        {
            image = ImageIO.read(in);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void prepareToSerialization()
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try
        {
            ImageIO.write(image, "png", baos);
            baos.flush();
            imageByte = baos.toByteArray();
            baos.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public String getAppliedIPlugin()
    {
        return appliedIPlugin;
    }
}
