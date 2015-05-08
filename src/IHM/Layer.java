package IHM;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by Gabriel on 04/05/2015.
 */
public class Layer implements Serializable
{
    private transient BufferedImage image;
    private String name;
    private byte[] imageByte;
    private boolean visible;

    public Layer(String name, BufferedImage image)
    {
        this.image = image;
        this.name = name;
        this.visible = true;
    }

    public BufferedImage getImage()
    {
        return image;
    }

    public void setImage(BufferedImage image)
    {
        this.image = image;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Create the bufferImage after deserialization.
     */
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

    public boolean isVisible()
    {
        return visible;
    }

    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }
}
