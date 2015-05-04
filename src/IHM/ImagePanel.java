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
//	private transient BufferedImage image;
	private transient ArrayList<BufferedImage> layers = new ArrayList<>();
	private byte[] imageByte;

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
//		this.image = image;
		layers.add(image);
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
		width = image.getWidth();
		height = image.getHeight();
		imageType = image.getType();
		pixels = new int[width * height];
		image.getRGB(0, 0, width, height, pixels, 0, width);
		setBackground(new Color(80, 80, 80));
		//
		layers.add(image);
	}

	/**
	 * Create the bufferImage after deserialization.
	 */
	public void buildImage()
	{
//		image = new BufferedImage(width, height, imageType);
		BufferedImage image = new BufferedImage(width, height, imageType);
		image.setRGB(0, 0, width, height, pixels, 0, width);

		InputStream in = new ByteArrayInputStream(imageByte);
		try
		{
			image = ImageIO.read(in);
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		layers.add(image);
	}

	public void prepareToSerialization()
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try
		{
//			ImageIO.write(image, "png", baos);
			ImageIO.write(layers.get(0), "png", baos);
			baos.flush();
			imageByte = baos.toByteArray();
			baos.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
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
		return layers.get(0);
	}

	public synchronized void setImage(BufferedImage image)
	{
//		this.image = image;
		layers.set(0, image);
		this.height = image.getHeight();
		this.width = image.getWidth();
	}

	@Override
	public void paintComponent(Graphics g)
	{
//		System.out.println("Height: " + image.getHeight() + ",Width: " + image.getWidth());
		super.paintComponent(g);
//		g.drawImage(image, 0, 0, null);
		for (BufferedImage i : layers)
			g.drawImage(i, 0, 0, null);
	}

	public void drawImage(int x, int y, BufferedImage bufferedImage)
	{
//		Graphics g = image.getGraphics();
		Graphics g = layers.get(0).getGraphics();
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
		BufferedImage image = layers.get(0);
		if (pageIndex > 0)
			return NO_SUCH_PAGE;
		Graphics2D g = (Graphics2D)graphics;
		g.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
		int x = (int)Math.round((pageFormat.getImageableWidth() - image.getWidth() / 2f));
		int y = (int)Math.round((pageFormat.getImageableHeight() - image.getHeight() / 2f));
		g.drawImage(image, x, y, null);

		return PAGE_EXISTS;
	}
}
