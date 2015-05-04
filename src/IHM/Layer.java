package IHM;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Gabriel on 04/05/2015.
 */
public class Layer
{
    private BufferedImage image;
    private String name;
    private byte[] imageByte;

    public Layer(String name, BufferedImage image)
    {
        this.image = image;
        this.name = name;
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
}
