package Model;

import Control.PluginExecutor;
import Control.ProjectMouseController;
import IHM.ImagePanel;
import IHM.Layer;
import plugin.IPlugin;

import javax.imageio.ImageIO;
import java.awt.*;
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

    private ProjectMouseController projectMouseController;

    public Project(Observer o, Model model)
    {
        projectMouseController = new ProjectMouseController(this, model);
        addObserver(o);
        projectName = getNewProjectName(model.getProjects(), "New Project");
        BufferedImage newImage = new BufferedImage(320, 180, BufferedImage.TYPE_INT_ARGB);
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
        imagePanel.prepareToSerialization();
    }

    // Observer required when deserialized
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
            PluginExecutor pluginExecutor = new PluginExecutor(this, imagePanel.getImage(), plugin);
            pluginThread = new Thread(pluginExecutor);
            pluginThread.start();
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
        System.out.println(imagePanel.getLayers().size());
        System.out.println(layer);
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
        if (layer >= 0 && layer < getLayers().size())
            getLayers().remove(layer);
        setChanged();
        notifyObservers(this);
    }

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
        if (isPluginRunning == false && imagePanel.previewPencil.size() > 0)
        {
            isPluginRunning = true;
            BufferedImage image = imagePanel.getImage();
            if (image != null)
            {
                image = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
                Graphics2D g = (Graphics2D) image.getGraphics();
                g.drawImage(imagePanel.getImage(), 0, 0, null);
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setColor(imagePanel.pencilColor);
                int width = imagePanel.getWidth();
                int height = imagePanel.getHeight();
                int realWidth = imagePanel.getImage().getWidth();
                int realHeight = imagePanel.getImage().getHeight();
                for (Point p : imagePanel.previewPencil)
                {
                    int posX = p.x * realWidth / width;
                    int posY = p.y * realHeight / height;
                    g.fillOval(posX - imagePanel.pencilSize / 2, posY - imagePanel.pencilSize / 2, imagePanel.pencilSize, imagePanel.pencilSize);
                }
                g.dispose();
                setImageChanges(image, "Pencil Tool");
            }
            isPluginRunning = false;
        }
        setChanged();
        notifyObservers(this);
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
