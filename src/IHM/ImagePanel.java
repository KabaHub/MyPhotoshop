package IHM;

import View.CustomComponents.CustomJPanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * We give you this class to help you display images.
 * You are free to use it or not, to modify it.
 */
public class ImagePanel extends CustomJPanel implements Serializable, Scrollable, Printable
{
	private static final long serialVersionUID = -314171089120047242L;
	private String fileName;
	private int width;
	private int height;
	private int imageType;
	private int[] pixels;
	private ArrayList<Layer> layers = new ArrayList<>();

	/**
	 * Create the ImagePanel
	 *
	 * @param image: image to display
	 * @param name: name of the image
	 */
	public ImagePanel(BufferedImage image, String name)
	{
		super(CustomJPanel.GREY);
		fileName = name;
		layers.add(new Layer("Layer 0", image));
		width = image.getWidth();
		height = image.getHeight();
		imageType = image.getType();
		pixels = new int[width * height];
		image.getRGB(0, 0, width, height, pixels, 0, width);
		setBackground(new Color(80, 80, 80));
	}

	/**
	 * Create the ImagePanel
	 *
	 * @param file: image to display
	 */
	public ImagePanel(File file)
	{
		super(CustomJPanel.GREY);
		BufferedImage image = null;
		try
		{
			image = ImageIO.read(file);
			fileName = file.getPath();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		assert image != null;
		width = image.getWidth();
		height = image.getHeight();
		imageType = image.getType();
		pixels = new int[width * height];
		image.getRGB(0, 0, width, height, pixels, 0, width);
		setBackground(new Color(80, 80, 80));
		layers.add(new Layer("Layer 0", image));
	}

	/**
	 * Create the bufferImage after deserialization.
	 */
	public void buildImage()
	{
		layers.forEach(IHM.Layer::buildImage);
	}

	public void prepareToSerialization()
	{
		layers.forEach(IHM.Layer::prepareToSerialization);
	}

	@Override
	public int getWidth()
	{
		return width;
	}

	@Override
	public int getHeight()
	{
		return height;
	}

	public BufferedImage getImage()
	{
		return layers.get(0).getImage();
	}

	public synchronized void setImage(BufferedImage image)
	{
		layers.get(0).setImage(image);
		this.height = image.getHeight();
		this.width = image.getWidth();
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		for (Layer l : layers)
		{
			if (l.isVisible())
				g.drawImage(l.getImage(), 0, 0, null);
		}
	}

	public void drawImage(int x, int y, BufferedImage bufferedImage)
	{
		Graphics g = layers.get(0).getImage().getGraphics();
		g.drawImage(bufferedImage, x, y, null);
		g.dispose();
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public ArrayList<Layer> getLayers()
	{
		return layers;
	}

	@Override
	public Dimension getPreferredSize() {
		Dimension size = super.getPreferredSize();
		size.width = width;
		size.height = height;
		return size;
	}

	@Override
	public Dimension getPreferredScrollableViewportSize()
	{
		return getPreferredSize();
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction)
	{
		return 100;
	}

	@Override
	public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction)
	{
		return 200;
	}

	@Override
	public boolean getScrollableTracksViewportWidth()
	{
		return false;
	}

	@Override
	public boolean getScrollableTracksViewportHeight()
	{
		return false;
	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException
	{
		BufferedImage image = layers.get(0).getImage();
		if (pageIndex > 0)
			return NO_SUCH_PAGE;
		Graphics2D g = (Graphics2D)graphics;
		g.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
//		int x = (int)Math.round((pageFormat.getImageableWidth() - image.getWidth() / 2f));
//		int y = (int)Math.round((pageFormat.getImageableHeight() - image.getHeight() / 2f));
		g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);

		return PAGE_EXISTS;
	}
}
