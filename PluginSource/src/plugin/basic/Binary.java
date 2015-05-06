package plugin.basic;

import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;

import plugin.IPlugin;

import javax.swing.*;
import javax.swing.GrayFilter;

public class Binary implements IPlugin
{

	@Override
	public BufferedImage perform(BufferedImage img)
	{
		BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_BINARY);

		Graphics g = res.getGraphics();
		g.drawImage(img, 0, 0, null);
		g.dispose();

		BufferedImage res2= new BufferedImage(res.getWidth(), res.getHeight(), BufferedImage.TYPE_INT_ARGB);

		Graphics g2 = res2.getGraphics();
		g2.drawImage(res, 0, 0, null);
		g2.dispose();

		return res2;
	}

	@Override
	public String getName()
	{
		return "Binary";
	}

}
