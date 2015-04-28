package plugin.bonus;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.Random;

import plugin.IPlugin;

public class Blur implements IPlugin {

	@Override
	public BufferedImage perform(BufferedImage img) 
	{
		float[] matrice = {
				0.1f, 0.1f, 0.1f,
				0.1f, 0.2f, 0.1f,
				0.1f, 0.1f, 0.1f
		};
		BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, matrice));
		BufferedImage res = op.filter(img, null);

		return res;
	}

	@Override
	public String getName() 
	{
		return "Blur";
	}

}
