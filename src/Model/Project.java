package Model;

import Control.PluginExecutor;
import Control.ProjectMouseController;
import IHM.ImagePanel;
import IHM.Layer;
import plugin.IPlugin;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Gabriel on 23/04/2015.
 */
public class Project extends Observable implements Serializable
{
    private static final long serialVersionUID = -403250971215465050L;
    private String projectName;
    private ImagePanel imagePanel;
    private int currentState = -1;
    private ArrayList<ImageState> history = new ArrayList<>();

    private transient Thread pluginThread;
    private boolean isPluginRunning = false;

    private transient ProjectMouseController projectMouseController;

    public Project(Observer o, Model model, int width, int height)
    {
        projectMouseController = new ProjectMouseController(this, model);
        addObserver(o);
        projectName = getNewProjectName(model.getProjects(), "New Project");
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        imagePanel = new ImagePanel(newImage, projectName);
        imagePanel.addMouseWheelListener(projectMouseController);
        imagePanel.addMouseListener(projectMouseController);
        imagePanel.addMouseMotionListener(projectMouseController);
        addToHistory("Original", imagePanel);
    }

    public Project(Observer o, Model model, File file)
    {
        projectMouseController = new ProjectMouseController(this, model);
        addObserver(o);
        projectName = file.getName();
        int n = projectName.lastIndexOf('.');
        if (n != -1)
            projectName = projectName.substring(0, n);
        projectName = getNewProjectName(model.getProjects(), projectName);
        imagePanel = new ImagePanel(file);
        imagePanel.addMouseWheelListener(projectMouseController);
        imagePanel.addMouseListener(projectMouseController);
        imagePanel.addMouseMotionListener(projectMouseController);
        addToHistory("Original", imagePanel);
    }

    public Project(Observer o, Model model, BufferedImage bufferedImage, String name)
    {
        projectMouseController = new ProjectMouseController(this, model);
        addObserver(o);
        projectName = getNewProjectName(model.getProjects(), name);
        imagePanel = new ImagePanel(bufferedImage, name);
        imagePanel.addMouseWheelListener(projectMouseController);
        imagePanel.addMouseListener(projectMouseController);
        imagePanel.addMouseMotionListener(projectMouseController);
        addToHistory("Original", imagePanel);
    }

    public boolean isHistoryEmpty()
    {
        return (history.size() == 1);
    }

    public void prepareToSerialization()
    {
        for (MouseListener ml : imagePanel.getMouseListeners())
            imagePanel.removeMouseListener(ml);
        for (MouseWheelListener mwl : imagePanel.getMouseWheelListeners())
            imagePanel.removeMouseWheelListener(mwl);
        for (MouseMotionListener mml : imagePanel.getMouseMotionListeners())
            imagePanel.removeMouseMotionListener(mml);
        imagePanel.prepareToSerialization();
    }

    // Observer and Model required when deserialize
    public void buildProject(Observer o, Model model)
    {
        projectMouseController = new ProjectMouseController(this, model);
        addObserver(o);
        imagePanel.buildImage();
        imagePanel.addMouseWheelListener(projectMouseController);
        imagePanel.addMouseListener(projectMouseController);
        imagePanel.addMouseMotionListener(projectMouseController);
        for (ImageState i : history)
            i.buildImage();
    }

    public ImagePanel getImagePanel()
    {
        return imagePanel;
    }

    @Deprecated
    public void setImagePanel(BufferedImage image)
    {
        imagePanel.setImage(image);
        setChanged();
        notifyObservers(this);
    }

    public void setImageChanges(BufferedImage image, String pluginName)
    {
        imagePanel.setImage(image);
        addToHistory(pluginName, imagePanel);
        setChanged();
        notifyObservers(this);
    }

    public void importImage(File file)
    {
        BufferedImage image = null;
        try
        {
            image = ImageIO.read(file);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        imagePanel.setImage(image);
        addToHistory("Imported File " + file.getName(), imagePanel);
        setChanged();
        notifyObservers(this);
    }

    public void save(String pluginName)
    {
        addToHistory(pluginName, imagePanel);
    }

    private void addToHistory(String pluginName, ImagePanel imagePanel)
    {
        currentState++;
        history.subList(currentState, history.size()).clear();
        ImageState imageState = new ImageState(pluginName, imagePanel.getLayers(), getCurrentLayer());
        history.add(imageState);
    }

    public void undo()
    {
        if (currentState > 0 && currentState <= history.size())
        {
            currentState--;
            imagePanel.restoreImage(history.get(currentState).getLayers());
            setChanged();
            notifyObservers(this);
        }
    }

    public void redo()
    {
        if (currentState >= 0 && currentState + 1 < history.size())
        {
            currentState++;
            imagePanel.restoreImage(history.get(currentState).getLayers());
            setChanged();
            notifyObservers(this);
        }
    }

    public void applyPlugin(IPlugin plugin)
    {
        if (!isPluginRunning)
        {
            isPluginRunning = true;
            BufferedImage img = imagePanel.getImage();
            if (img != null)
            {
                PluginExecutor pluginExecutor = new PluginExecutor(this, img, plugin);
                pluginThread = new Thread(pluginExecutor);
                pluginThread.start();
            }
        }
    }

    public void stopPlugin()
    {
        if (isPluginRunning)
        {
            pluginThread.stop();
//            pluginThread.interrupt();
            isPluginRunning = false;
        }
    }

    public boolean isPluginRunning()
    {
        return isPluginRunning;
    }

    public void setPluginRunning(boolean b)
    {
        isPluginRunning = b;
    }

    public ArrayList<ImageState> getHistory()
    {
        return history;
    }

    public String getProjectName()

    {
        return projectName;
    }

    public void setProjectName(String name)
    {
        projectName = name;
    }

    public static String getNewProjectName(ArrayList<Project> projects, String name)
    {
        boolean exists = true;
        int number = 1;
        while (exists)
        {
            exists = false;
            for (Project p : projects)
            {
                if (p.getProjectName().contentEquals(name))
                {
                    number++;
                    int n = name.lastIndexOf('_');
                    if (n == -1)
                        name += '_';
                    else
                    {
                        try
                        {
                            number = Integer.parseInt(name.substring(n + 1));
                            if (number > 1)
                                number++;
                            name = name.substring(0, n + 1);
                            exists = true;
                        } catch (NumberFormatException e)
                        {
                            name += '_';
                        }
                    }
                    name += number;
                }
            }
        }
        return name;
    }

    public int getCurrentState()
    {
        return currentState;
    }

    public void setCurrentState(int newState)
    {
        if (newState >= 0 && newState < history.size())
        {
            currentState = newState;
            imagePanel.restoreImage(history.get(currentState).getLayers());
            setCurrentLayer(history.get(currentState).getCurrentLayer());
            setChanged();
            notifyObservers(this);
        }
    }

    public ArrayList<Layer> getLayers()
    {
        return imagePanel.getLayers();
    }

    public void addLayer()
    {
        imagePanel.addLayer();
        addToHistory("Added Layer", imagePanel);
        setChanged();
        notifyObservers(this);
    }

    public int getCurrentLayer()
    {
        return imagePanel.getCurrentLayer();
    }

    public void setCurrentLayer(int layer)
    {
        if (imagePanel.getLayers().size() > layer)
            imagePanel.setCurrentLayer(layer);
        else
            imagePanel.setCurrentLayer(0);
        setChanged();
        notifyObservers(this);
    }

    public void setLayerVisible(int layer, boolean b)
    {
        Layer l = getLayers().get(layer);
        int i = imagePanel.getLayers().indexOf(l);
        if (i != -1)
            imagePanel.getLayers().get(i).setVisible(b);
        setChanged();
        notifyObservers(this);
    }

    public boolean getLayerVisible(int layer)
    {
        return getLayers().get(layer).isVisible();
    }

    @Deprecated
    private String getFileExtension(File file)
    {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf('.');
        if (lastIndexOf == -1)
            return "";
        return name.substring(lastIndexOf);
    }

    public void swapLayerUp(int layer)
    {
        if (layer >= 0 && layer < getLayers().size() - 1)
        {
            Collections.swap(getLayers(), layer, layer + 1);
            setCurrentLayer(layer + 1);
        }
        setChanged();
        notifyObservers(this);
    }

    public void swapLayerDown(int layer)
    {
        if (layer > 0 && layer < getLayers().size())
        {
            Collections.swap(getLayers(), layer, layer - 1);
            setCurrentLayer(layer - 1);
        }
        setChanged();
        notifyObservers(this);
    }

    public void closeLayer(int layer)
    {
        if (getLayers().size() == 1)
        {
            JOptionPane.showMessageDialog(null, "Cannot Close last Layer!", "Error", JOptionPane.ERROR_MESSAGE);
        } else
        {
            if (layer >= 0 && layer < getLayers().size())
                getLayers().remove(layer);
            if (getCurrentLayer() >= getLayers().size())
                setCurrentLayer(getLayers().size() - 1);
            setChanged();
            notifyObservers(this);
        }
    }

    @Deprecated
    public float getZoom()
    {
        return imagePanel.getZoom();
    }

    public void setZoom(float zoom)
    {
        imagePanel.setZoom(zoom);
        setChanged();
        notifyObservers(this);
    }

    public void drawPencil()
    {
        ToolType toolType = imagePanel.toolType;
        if (!isPluginRunning && imagePanel.previewPencil.size() > 0)
        {
            isPluginRunning = true;
            BufferedImage image = imagePanel.getImage();

            BufferedImage erasedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            if (toolType == ToolType.ERASER_TOOL && imagePanel.getCurrentLayer() != 0)
            {
                for (int i = 0; i < erasedImage.getWidth(); i++)
                    for (int j = 0; j < erasedImage.getHeight(); j++)
                    {
                        erasedImage.setRGB(i, j, imagePanel.getLayers().get(imagePanel.getCurrentLayer()).getImage().getRGB(i, j));
                        for (Point p : imagePanel.previewPencil)
                            if (i > p.x - imagePanel.pencilSize / 2 && i < p.x + imagePanel.pencilSize / 2 && j > p.y - imagePanel.pencilSize / 2 && j < p.y + imagePanel.pencilSize / 2)
                                erasedImage.setRGB(i, j, new Color(255, 0, 0, 0).getRGB());
                    }
            }

            image = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
            Graphics2D g = (Graphics2D) image.getGraphics();
            g.setBackground(new Color(80, 80, 80));
            if (!(toolType == ToolType.ERASER_TOOL && imagePanel.getCurrentLayer() != 0))
                g.drawImage(imagePanel.getImage(), 0, 0, null);

            if (toolType == ToolType.BRUSH_TOOL)
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (toolType == ToolType.PENCIL_TOOL || toolType == ToolType.BRUSH_TOOL)
            {
                g.setColor(imagePanel.pencilColor);
                int width = imagePanel.getWidth();
                int height = imagePanel.getHeight();
                int realWidth = imagePanel.getImage().getWidth();
                int realHeight = imagePanel.getImage().getHeight();
                BasicStroke line = new BasicStroke(imagePanel.pencilSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
                if (imagePanel.pencilType == "Square")
                    line = new BasicStroke(imagePanel.pencilSize, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER);
                g.setStroke(line);
                ArrayList<Point> previewPencil = imagePanel.previewPencil;
                for (int j = 0; j < previewPencil.size() - 1; j++)
                {
                    Point p = previewPencil.get(j);
                    Point next = previewPencil.get(j + 1);
                    int posX = p.x * realWidth / width;
                    int posY = p.y * realHeight / height;
                    int nextPosX = next.x * realWidth / width;
                    int nextPosY = next.y * realHeight / height;
                    g.drawLine(posX, posY, nextPosX, nextPosY);
                }
            } else if (toolType == ToolType.ERASER_TOOL)
            {
                if (getCurrentLayer() != 0)
                {
                    g.drawImage(erasedImage, 0, 0, null);
                } else
                {
                    int width = imagePanel.getWidth();
                    int height = imagePanel.getHeight();
                    int realWidth = imagePanel.getImage().getWidth();
                    int realHeight = imagePanel.getImage().getHeight();
                    g.setColor(new Color(80, 80, 80));
                    for (Point p : imagePanel.previewPencil)
                    {
                        int posX = p.x * realWidth / width;
                        int posY = p.y * realHeight / height;
                        g.fillRect(posX - imagePanel.pencilSize / 2, posY - imagePanel.pencilSize / 2, imagePanel.pencilSize, imagePanel.pencilSize);
                    }
                }
            }
            g.dispose();
            setImageChanges(image, "Pencil Tool");
            isPluginRunning = false;
            setChanged();
            notifyObservers(this);
        }
    }

    public void addToPencilPreview(Point p)
    {
        imagePanel.previewPencil.add(p);
        setChanged();
        notifyObservers(this);
    }

    public void clearPencilPreview()
    {
        imagePanel.previewPencil.clear();
        setChanged();
        notifyObservers(this);
    }
}
