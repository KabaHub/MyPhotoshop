package plugin.bonus;

import java.awt.*;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.Random;

import plugin.IPlugin;

public class SobelBorders implements IPlugin {

	@Override
	public BufferedImage perform(BufferedImage img) 
	{
		float[] matrice = {
				1f, 0f, -1f,
				3f, 0f, -3f,
				1f, 0f, -1f
		};
		BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, matrice), ConvolveOp.EDGE_NO_OP, null);
		BufferedImage res = op.filter(img, null);

		float[] matrice2 = {
				1f, 3f, 1f,
				0f, 0f, 0f,
				-1f, -3f, -1f
		};
		op = new ConvolveOp(new Kernel(3, 3, matrice2), ConvolveOp.EDGE_NO_OP, null);
		res = op.filter(res, null);

		return res;
	}

	@Override
	public String getName() 
	{
		return "Sobel Filter (Borders)";
	}
}
