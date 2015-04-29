package plugin.bonus;

import java.awt.*;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.Random;

import plugin.IPlugin;

public class LaplaceFilter implements IPlugin {

	@Override
	public BufferedImage perform(BufferedImage img) 
	{
		float[] matrice = {
				-1f, -1f, -1f,
				-1f, 8f, -1f,
				-1f, -1f, -1f
		};
		BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, matrice), ConvolveOp.EDGE_NO_OP, null);
		BufferedImage res = op.filter(img, null);

		return res;
	}

	@Override
	public String getName() 
	{
		return "Laplace Filter (Light Variation)";
	}
}
