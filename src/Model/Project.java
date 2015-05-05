package Model;

import Control.PluginExecutor;
import IHM.ImagePanel;
import IHM.Layer;
import plugin.IPlugin;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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

    public Project(Observer o, Model model)
    {
        addObserver(o);
        projectName = getNewProjectName(model.getProjects(), "New Project");
        BufferedImage newImage = new BufferedImage(320, 180, BufferedImage.TYPE_INT_ARGB);
        imagePanel = new ImagePanel(newImage, projectName);
        addToHistory("Original", imagePanel);
    }

    public Project(Observer o, Model model, File file)
    {
        addObserver(o);
        projectName = file.getName();
        int n = projectName.lastIndexOf('.');
        if (n != -1)
            projectName = projectName.substring(0, n);
        projectName = getNewProjectName(model.getProjects(), projectName);
        imagePanel = new ImagePanel(file);
        addToHistory("Original", imagePanel);
    }

    public Project(Observer o, Model model, BufferedImage bufferedImage, String name)
    {
        addObserver(o);
        projectName = getNewProjectName(model.getProjects(), name);
        imagePanel = new ImagePanel(bufferedImage, name);
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
    public void buildProject(Observer o)
    {
        addObserver(o);
        imagePanel.buildImage();
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

    private void addToHistory(String pluginName, ImagePanel imagePanel)
    {
        currentState++;
        history.subList(currentState, history.size()).clear();
        ImageState imageState = new ImageState(pluginName, imagePanel.getLayers());
        history.add(imageState);
    }

    public void undo()
    {
        if (currentState > 0 && currentState <= history.size())
        {
            currentState--;
//            imagePanel.setImage(history.get(currentState).getImage());
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
//            imagePanel.setImage(history.get(currentState).getImage());
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
//            imagePanel.setImage(history.get(currentState).getImage());
            imagePanel.restoreImage(history.get(currentState).getLayers());
            setChanged();
            notifyObservers(this);
        }
    }

    public ArrayList<Layer> getLayers()
    {
        return imagePanel.getLayers();
    }

    public void setLayerVisible(Layer l, boolean b)
    {
        int i = imagePanel.getLayers().indexOf(l);
        if (i != -1)
            imagePanel.getLayers().get(i).setVisible(b);
        setChanged();
        notifyObservers(this);
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
}
