package plugin.basic;

import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.RescaleOp;
import java.util.Random;

import plugin.IPlugin;

public class Invert implements IPlugin
{
	@Override
	public BufferedImage perform(BufferedImage img)
	{
		RescaleOp op = new RescaleOp(-1.0f, 255f, null);
		BufferedImage res = op.filter(img, null);

		return res;
	}

	@Override
	public String getName()
	{
		return "Invert";
	}

}
