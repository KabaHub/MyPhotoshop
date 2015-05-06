package plugin.basic;

import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;

import plugin.IPlugin;

import javax.swing.*;
import javax.swing.GrayFilter;

public class GrayScale implements IPlugin
{
    @Override
    public BufferedImage perform(BufferedImage img)
    {
//
        BufferedImage newImage = new BufferedImage(img.getHeight(), img.getWidth(), BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < img.getWidth(); i++)
        {
            for (int j = 0; j < img.getHeight(); j++)
            {
                Color c = new Color(img.getRGB(i, j), true);
                int grey = (c.getRed() + c.getGreen() + c.getBlue())/3;
                c = new Color(grey, grey, grey, c.getAlpha());
                newImage.setRGB(i, j, c.getRGB());
            }
        }

        return newImage;
    }

    @Override
    public String getName()
    {
        return "GrayScale";
    }
}
