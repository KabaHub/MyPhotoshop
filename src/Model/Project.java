package Model;

import IHM.ImagePanel;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Gabriel on 23/04/2015.
 */
public class Project extends Observable implements Serializable
{
    private String projectName;
    private ImagePanel imagePanel;
    private ArrayList<ImageState> history = new ArrayList<>();

    public Project(Observer o)
    {
        addObserver(o);
        projectName = "New Project";
        BufferedImage newImage = new BufferedImage(320, 180, BufferedImage.TYPE_INT_ARGB);
        imagePanel = new ImagePanel(newImage, projectName);
    }

    public Project(Observer o, File file)
    {
        addObserver(o);
        projectName = file.getName();
        imagePanel = new ImagePanel(file);
    }

    public Project(Observer o, BufferedImage bufferedImage, String name)
    {
        addObserver(o);
        projectName = name;
        imagePanel = new ImagePanel(bufferedImage, name);
    }

    public boolean isHistoryEmpty()
    {
        return history.isEmpty();
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
        ImageState imageState = new ImageState(this, pluginName, image);
        history.add(imageState);
        imagePanel.setImage(image);
        setChanged();
        notifyObservers(this);
    }

    public ArrayList<ImageState> getHistory()
    {
        return history;
    }

    public String getProjectName()
    {
        return projectName;
    }
}
