package plugin.basic;

import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.lang.System;

import plugin.IPlugin;

public class RotateRight implements IPlugin
{

	@Override
	public BufferedImage perform(BufferedImage img)
	{
		AffineTransform at = new AffineTransform();
		int w = img.getWidth();
		int h = img.getHeight();
		at.translate(h / 2, w / 2);
		at.rotate(Math.PI / 2);
		at.translate(-w / 2, -h / 2);
		AffineTransformOp ato = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		BufferedImage res = ato.filter(img, null);

		return res;
	}


	@Override
	public String getName()
	{
		return "Rotate Right (90°)";
	}

}
