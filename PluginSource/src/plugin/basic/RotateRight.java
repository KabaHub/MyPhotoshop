package plugin.basic;

import java.awt.*;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import plugin.IPlugin;

public class RotateRight implements IPlugin
{

	@Override
	public BufferedImage perform(BufferedImage img)
	{
		AffineTransform at = new AffineTransform();
		at.rotate(Math.PI / 2, img.getWidth() / 2, img.getHeight() / 2);
		AffineTransformOp ato = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
		BufferedImage res = ato.filter(img, null);

		return res;
	}

	@Override
	public String getName()
	{
		return "Rotate Right (90°)";
	}

}
