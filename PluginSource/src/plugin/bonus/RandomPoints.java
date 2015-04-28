package plugin.bonus;

import java.awt.image.BufferedImage;
import java.util.Random;

import plugin.IPlugin;

public class RandomPoints implements IPlugin {

	@Override
	public BufferedImage perform(BufferedImage img) 
	{
		BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
		
		for (int i = 0; i < img.getWidth(); i++)
			for (int j = 0; j < img.getHeight(); j++)
				res.setRGB(i, j, new Random().nextInt());
		
		return res;
	}

	@Override
	public String getName() 
	{
		return "Random Points";
	}

}
