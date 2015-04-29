package plugin.basic;

import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import plugin.IPlugin;

public class RotateLeft implements IPlugin
{

	@Override
	public BufferedImage perform(BufferedImage img)
	{
		AffineTransform at = new AffineTransform();
		int w = img.getWidth();
		int h = img.getHeight();
		at.translate(w / 2, h / 2);
		at.rotate(-Math.PI / 2);
		at.translate(-h / 2, -w / 2);
		AffineTransformOp ato = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		BufferedImage res = ato.filter(img, null);

		return res;
	}


	@Override
	public String getName()
	{
		return "Rotate Left (90�)";
	}

}
