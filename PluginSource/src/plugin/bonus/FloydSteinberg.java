package plugin.bonus;

import java.awt.*;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.lang.System;
import java.util.Random;

import plugin.IPlugin;

public class FloydSteinberg implements IPlugin
{
    @Override
    public BufferedImage perform(BufferedImage img)
    {
        BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        int w = img.getWidth();
        int h = img.getHeight();

        int[][] greyImg = new int[w][h];
        for (int i = 0; i < w; i++)
            for (int j = 0; j < h; j++)
            {
                Color c = new Color(img.getRGB(i, j));
                greyImg[i][j] = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
            }

        for (int j = 0; j < h; j++)
            for (int i = 0; i < w; i++)
            {
                int oldValue = greyImg[i][j];
                int newColor = oldValue < 128 ? 0x000000 : 0xFFFFFF;
                res.setRGB(i, j, newColor);
                int error = oldValue - (oldValue < 128 ? 0 : 255);

                if (i+1 < w)
                    greyImg[i + 1][j] += error * 7 / 16;
                if (i-1 > 0 && j+1 < h)
                    greyImg[i-1][j+1] += error * 3 / 16;
                if (j+1 < h)
                    greyImg[i][j+1] += error * 5 / 16;
                if (i+1 < w && j+1 < h)
                    greyImg[i+1][j+1] += error * 1 / 16;
            }
        return res;
    }

    @Override
    public String getName()
    {
        return "Floyd-Steinberg (2bit)";
    }

}
