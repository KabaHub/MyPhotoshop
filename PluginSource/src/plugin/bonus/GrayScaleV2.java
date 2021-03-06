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

public class GrayScaleV2 implements IPlugin
{
    @Override
    public BufferedImage perform(BufferedImage img)
    {
        BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

        Graphics g = res.getGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();

        BufferedImage res2 = new BufferedImage(res.getWidth(), res.getHeight(), BufferedImage.TYPE_INT_ARGB);

        g = res2.getGraphics();
        g.drawImage(res, 0, 0, null);
        g.dispose();

        return res2;
    }

    @Override
    public String getName()
    {
        return "GrayScale (alternative)";
    }
}
