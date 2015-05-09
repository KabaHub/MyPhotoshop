package IHM;

import Control.ProjectMouseController;
import View.CustomComponents.CustomJPanel;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AreaAveragingScaleFilter;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

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
    private int currentLayer = 0;
    private ArrayList<Layer> layers = new ArrayList<>();
    private float zoom = 1f;

    public ArrayList<Point> previewPencil = new ArrayList<>();
    public int pencilSize = 1;
    public Color pencilColor = Color.BLACK;

    /**
     * Create the ImagePanel
     *
     * @param image: image to display
     * @param name:  name of the image
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
        return (int) (width * zoom);
    }

    @Override
    public int getHeight()
    {
        return (int) (height * zoom);
    }

    public BufferedImage getImage()
    {
        return layers.get(currentLayer).getImage();
    }

    public synchronized void restoreImage(ArrayList<Layer> layers)
    {
        this.layers.clear();
        this.layers.addAll(layers.stream().map(l -> new Layer(l.getName(), l.getImage())).collect(Collectors.toList()));
    }

    public synchronized void setImage(BufferedImage image)
    {
        layers.get(currentLayer).setImage(image);
        int maxWidth = 0;
        int maxHeight = 0;
        for (Layer l : layers)
        {
            maxWidth = Math.max(maxWidth, l.getImage().getWidth());
            maxHeight = Math.max(maxHeight, l.getImage().getHeight());
        }
        this.width = maxWidth;
        this.height = maxHeight;
        // Bad Perf
        for (Layer l : layers)
        {
            BufferedImage newI = new BufferedImage(this.width, this.height, l.getImage().getType());
            Graphics g = newI.getGraphics();
            g.drawImage(l.getImage(), 0, 0, null);
            g.dispose();
            l.setImage(newI);
        }
    }

    public void addLayer()
    {
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        layers.add(++currentLayer , new Layer("Layer " + layers.size(), newImage));
    }

    public int getCurrentLayer()
    {
        return currentLayer;
    }

    public void setCurrentLayer(int currentLayer)
    {
        this.currentLayer = currentLayer;
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.scale(zoom, zoom);
        int i = 0;
        for (Layer l : layers)
        {
            if (l.isVisible())
            {
                g2d.drawImage(l.getImage(), 0, 0, null);
                if (i == currentLayer)
                {
                    // if tool == pencil
                    if (true)
                    {
                        g2d.setColor(pencilColor);
                        int width = getWidth();
                        int height = getHeight();
                        int realWidth = getImage().getWidth();
                        int realHeight = getImage().getHeight();
                        for (Point p : previewPencil)
                        {
                            int posX = p.x * realWidth / width;
                            int posY = p.y * realHeight / height;
                            g2d.fillOval(posX - pencilSize / 2, posY - pencilSize / 2, pencilSize, pencilSize);
                        }
                    }
                }
            }
            i++;
        }
//        layers.stream().filter(Layer::isVisible).forEach(l -> g.drawImage(l.getImage(), 0, 0, null));
    }

    @Deprecated
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

    public ArrayList<Layer> getLayers()
    {
        return layers;
    }

    @Override
    public Dimension getPreferredSize()
    {
        Dimension size = super.getPreferredSize();
        size.width = (int) (width * zoom);
        size.height = (int) (height * zoom);
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
        if (pageIndex > 0)
            return NO_SUCH_PAGE;
        Graphics2D g = (Graphics2D) graphics;
        g.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        for (Layer l : layers)
        {
            BufferedImage image = l.getImage();
            g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        }

        return PAGE_EXISTS;
    }

    public float getZoom()
    {
        return zoom;
    }

    public void setZoom(float zoom)
    {
        this.zoom += zoom;
    }
}
