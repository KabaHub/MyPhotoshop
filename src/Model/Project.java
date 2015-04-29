package Model;

import Control.PluginExecutor;
import IHM.ImagePanel;
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
    private ArrayList<ImageState> history = new ArrayList<>();

    private boolean isPluginRunning = false;

    public Project(Observer o, Model model)
    {
        addObserver(o);
        projectName = getNewProjectName(model.getProjects(), "New Project");
        BufferedImage newImage = new BufferedImage(320, 180, BufferedImage.TYPE_INT_ARGB);
        ImageState imageState = new ImageState("Original", newImage);
        history.add(imageState);
        imagePanel = new ImagePanel(newImage, projectName);
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
        ImageState imageState = new ImageState("Original", imagePanel.getImage());
        history.add(imageState);
    }

    public Project(Observer o, Model model, BufferedImage bufferedImage, String name)
    {
        addObserver(o);
        projectName = getNewProjectName(model.getProjects(), name);
        imagePanel = new ImagePanel(bufferedImage, name);
        ImageState imageState = new ImageState("Original", imagePanel.getImage());
        history.add(imageState);
    }

    public boolean isHistoryEmpty()
    {
        return (history.size() == 1);
    }

    public void prepareToSerialization()
    {
        imagePanel.prepareToSerialization();
    }

    public void buildProject()
    {
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
        ImageState imageState = new ImageState(pluginName, image);
        history.add(imageState);
        imagePanel.setImage(image);
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
        imagePanel.drawImage(0, 0, image);
        ImageState imageState = new ImageState("Imported File " + file.getName(), imagePanel.getImage());
        history.add(imageState);
        setChanged();
        notifyObservers(this);
    }

    public void applyPlugin(IPlugin plugin)
    {
        if (!isPluginRunning)
        {
            isPluginRunning = true;
            PluginExecutor pluginExecutor = new PluginExecutor(this, imagePanel.getImage(), plugin);
            Thread t = new Thread(pluginExecutor);
            t.start();
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


    private String getNewProjectName(ArrayList<Project> projects, String name)
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
                        }
                        catch (NumberFormatException e)
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

    private String getFileExtension(File file)
    {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf('.');
        if (lastIndexOf == -1)
            return "";
        return name.substring(lastIndexOf);
    }
}
