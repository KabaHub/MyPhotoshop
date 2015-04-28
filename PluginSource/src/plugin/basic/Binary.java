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

		return res;
	}

	@Override
	public String getName()
	{
		return "Binary";
	}

}
