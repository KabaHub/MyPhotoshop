package plugin.basic;

import plugin.IPlugin;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.System;

public class Invert implements IPlugin
{
    @Override
    public BufferedImage perform(BufferedImage img)
    {
        BufferedImage newImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < img.getWidth(); i++)
        {
            for (int j = 0; j < img.getHeight(); j++)
            {
                Color c = new Color(img.getRGB(i, j), true);
                Color invertColor = new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c.getBlue(), c.getAlpha());
                newImage.setRGB(i, j, invertColor.getRGB());
            }
        }

        return newImage;
    }

    @Override
    public String getName()
    {
        return "Invert";
    }

}
