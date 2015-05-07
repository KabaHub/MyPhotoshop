package plugin.bonus;

import java.awt.*;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.Random;

import plugin.IPlugin;

public class Emboss implements IPlugin {
	private Color convolvePoint(BufferedImage img, int x, int y, float[][] kernel)
    {
        int r = 0;
        int g = 0;
        int b = 0;
        int a = new Color(img.getRGB(x, y), true).getAlpha();
        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++)
            {
                Color lColor = new Color(img.getRGB(x, y));
                if (x + i > 0 && x + i < img.getWidth()
                        && y + j > 0 && y + j < img.getHeight())
                    lColor = new Color(img.getRGB(x + i, y + j));
                r += lColor.getRed() * kernel[1 + i][1 + j];
                g += lColor.getGreen() * kernel[1 + i][1 + j];
                b += lColor.getBlue() * kernel[1 + i][1 + j];
            }
        r = Math.max(r, 0);
        r = Math.min(r, 255);
        g = Math.max(g, 0);
        g = Math.min(g, 255);
        b = Math.max(b, 0);
        b = Math.min(b, 255);
        return new Color(r, g, b, a);
    }

    private BufferedImage convolve(BufferedImage img, float[] kernel)
    {
        // Convert kernel to 3x3 matrix
        float[][] k = new float[3][3];
        k[0][0] = kernel[0];
        k[0][1] = kernel[1];
        k[0][2] = kernel[2];
        k[1][0] = kernel[3];
        k[1][1] = kernel[4];
        k[1][2] = kernel[5];
        k[2][0] = kernel[6];
        k[2][1] = kernel[7];
        k[2][2] = kernel[8];
        BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < img.getWidth(); i++)
            for (int j = 0; j < img.getHeight(); j++)
            {
                Color c = new Color(img.getRGB(i, j));
                Color newColor = convolvePoint(img, i, j, k);
                res.setRGB(i, j, newColor.getRGB());
            }
        return res;
    }

	@Override
	public BufferedImage perform(BufferedImage img) 
	{
		float[] matrice = {
				-1f, -1f, 0f,
				-1f, 0f, 1f,
				0f, 1f, 1f
		};
		BufferedImage res = convolve(img, matrice);

		return res;
	}

	@Override
	public String getName()
	{
		return "Emboss filter";
	}
}
