package plugin.basic;

import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import plugin.IPlugin;

public class VerticalFlip implements IPlugin
{
	@Override
	public BufferedImage perform(BufferedImage img)
	{
		BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
		return res;
	}

	@Override
	public String getName()
	{
		return "Vertical Flip";
	}

}
