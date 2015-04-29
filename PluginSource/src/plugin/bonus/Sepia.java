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
		BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

		java.awt.Graphics graphics = res.getGraphics();
		graphics.drawImage(img, 0, 0, null);
		graphics.dispose();

		for (int i = 0; i < img.getWidth(); i++)
			for (int j = 0; j < img.getHeight(); j++)
			{
				Color c = new Color(res.getRGB(i, j));
				int r = (int)Math.min(255, ((c.getRed() * 0.394f) + (c.getGreen() * 0.768f) + (c.getBlue() * 0.188f)));
				int g = (int)Math.min(255, ((c.getRed() * 0.346f) + (c.getGreen() * 0.685f) + (c.getBlue() * 0.167f)));
				int b = (int)Math.min(255, ((c.getRed() * 0.271f) + (c.getGreen() * 0.535f) + (c.getBlue() * 0.132f)));
				c = new Color(r, g, b);
				res.setRGB(i, j, c.getRGB());
			}

		return res;
	}

	@Override
	public String getName() 
	{
		return "Sépia";
	}

}
