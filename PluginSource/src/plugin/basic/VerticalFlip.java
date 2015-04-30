package plugin.basic;

import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import plugin.IPlugin;

public class VerticalFlip implements IPlugin
{
	@Override
	public BufferedImage perform(BufferedImage img)
	{
		AffineTransform at = AffineTransform.getScaleInstance(1, -1);
		at.translate(0, -img.getHeight());
		AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		img = op.filter(img, null);

		return img;
	}

	@Override
	public String getName()
	{
		return "Vertical Flip";
	}

}
