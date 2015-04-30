package plugin.bonus;

import java.awt.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

import plugin.IPlugin;

public class Sepia implements IPlugin {

	@Override
	public BufferedImage perform(BufferedImage img) 
	{
		for (int i = 0; i < img.getWidth(); i++)
			for (int j = 0; j < img.getHeight(); j++)
			{
				Color c = new Color(img.getRGB(i, j));
				int r = (int)Math.min(((c.getRed() * 0.396f) + (c.getGreen() * 0.780f) + (c.getBlue() * 0.188f)), 255);
				int g = (int)Math.min(((c.getRed() * 0.340f) + (c.getGreen() * 0.675f) + (c.getBlue() * 0.160f)), 255);
				int b = (int)Math.min(((c.getRed() * 0.273f) + (c.getGreen() * 0.535f) + (c.getBlue() * 0.132f)), 255);
				c = new Color(r, g, b);
				img.setRGB(i, j, c.getRGB());
			}

		return img;
	}

	@Override
	public String getName() 
	{
		return "Sépia";
	}

}
