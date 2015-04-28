package Model;

import IHM.ImagePanel;

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

    public Project(Observer o)
    {
        addObserver(o);
        projectName = "New Project";
        BufferedImage newImage = new BufferedImage(320, 180, BufferedImage.TYPE_INT_ARGB);
        ImageState imageState = new ImageState("Original", newImage);
        history.add(imageState);
        imagePanel = new ImagePanel(newImage, projectName);
    }

    public Project(Observer o, File file)
    {
        addObserver(o);
        projectName = file.getName();
        imagePanel = new ImagePanel(file);
        ImageState imageState = new ImageState("Original", imagePanel.getImage());
        history.add(imageState);
    }

    public Project(Observer o, BufferedImage bufferedImage, String name)
    {
        addObserver(o);
        projectName = name;
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

    public void buildImage()
    {
        imagePanel.buildImage();
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

    public ArrayList<ImageState> getHistory()
    {
        return history;
    }

    public String getProjectName()
    {
        return projectName;
    }
}
