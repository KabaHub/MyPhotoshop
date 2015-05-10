package IHM;

import Control.ProjectMouseController;
import Model.ToolType;
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
    public ToolType toolType = ToolType.BRUSH_TOOL;
    public int pencilSize = 12;
    public String pencilType = "Circle";
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
        layers.add(++currentLayer, new Layer("Layer " + layers.size(), newImage));
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
        g2d.setBackground(new Color(80, 80, 80));
        g2d.scale(zoom, zoom);
        int i = 0;
        for (Layer l : layers)
        {
            if (l.isVisible())
            {
                g2d.drawImage(l.getImage(), 0, 0, null);
                if (i == currentLayer)
                {
                    if (toolType == ToolType.BRUSH_TOOL)
                        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    if (toolType == ToolType.PENCIL_TOOL || toolType == ToolType.BRUSH_TOOL)
                    {
                        g2d.setColor(pencilColor);
                        int width = getWidth();
                        int height = getHeight();
                        int realWidth = getImage().getWidth();
                        int realHeight = getImage().getHeight();
                        BasicStroke line = new BasicStroke(pencilSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
                        if (pencilType == "Square")
                            line = new BasicStroke(pencilSize, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER);
                        g2d.setStroke(line);
                        for (int j = 0; j < previewPencil.size() - 1; j++)
                        {
                            Point p = previewPencil.get(j);
                            Point next = previewPencil.get(j + 1);
                            int posX = p.x * realWidth / width;
                            int posY = p.y * realHeight / height;
                            int nextPosX = next.x * realWidth / width;
                            int nextPosY = next.y * realHeight / height;
                            g2d.drawLine(posX, posY, nextPosX, nextPosY);
                        }
                    } else if (toolType == ToolType.ERASER_TOOL)
                    {
                        int width = getWidth();
                        int height = getHeight();
                        int realWidth = getImage().getWidth();
                        int realHeight = getImage().getHeight();
                        g2d.setColor(new Color(80, 80, 80));
                        for (Point p : previewPencil)
                        {
                            int posX = p.x * realWidth / width;
                            int posY = p.y * realHeight / height;
                            g2d.fillRect(posX - pencilSize / 2, posY - pencilSize / 2, pencilSize, pencilSize);
                        }
                    }
                }
                i++;
            }
        }
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

    public Color getPixelColor(Point p)
    {
        return new Color(layers.get(currentLayer).getImage().getRGB(p.x, p.y));
    }

    public void moveCurrentLayer(ArrayList<Point> points)
    {
        if (points.size() > 1)
        {
            int mW = points.get(points.size() - 1).x - points.get(0).x;
            int mH = points.get(points.size() - 1).y - points.get(0).y;
            BufferedImage img = layers.get(currentLayer).getImage();
            BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            Graphics g = newImg.getGraphics();
            g.drawImage(img, mW, mH, null);
            g.dispose();
            setImage(newImg);
            this.repaint();
            points.clear();
        }
    }
}
