package plugin.basic;

import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import plugin.IPlugin;

public class RotateLeft implements IPlugin
{

    @Override
    public BufferedImage perform(BufferedImage img)
    {
        BufferedImage newImage = new BufferedImage(img.getHeight(), img.getWidth(), BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < img.getWidth(); i++)
        {
            for (int j = 0; j < img.getHeight(); j++)
            {
                Color c = new Color(img.getRGB(i, j), true);
                newImage.setRGB(j, img.getWidth() - i - 1, c.getRGB());
            }
        }

        return newImage;
    }


    @Override
    public String getName()
    {
        return "Rotate Left (90°)";
    }

}
