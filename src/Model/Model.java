package Model;

import plugin.IPlugin;
import plugin.PluginClassLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

/**
 * Created by Gabriel on 23/04/2015.
 */
public class Model extends Observable
{
    Observer o;
    private ArrayList<Project> projects = new ArrayList<>();
    private PluginClassLoader pluginClassLoader = new PluginClassLoader();
    private Hashtable<String, IPlugin> filters = new Hashtable<>();

    private ToolType currentTool = ToolType.NONE;
    private int pencilSize = 40;
    private String pencilType = "Circle";
    private Color chosenColor = Color.BLACK;

    public Model(Observer o)
    {
        this.o = o;
        addObserver(o);
        pluginClassLoader.loadDirectory("plugin");
        filters = pluginClassLoader.getClasses();
    }

    public Project addProject()
    {
        Project p = new Project(o, this);
        projects.add(p);
        setChanged();
        notifyObservers(p);
        return p;
    }

    public Project addProject(File file)
    {
        String ext = getFileExtension(file);
        if (Objects.equals(ext, ".myPSD"))
        {
            Project p = null;
            try
            {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                p = (Project) ois.readObject();
                String fileName = file.getName().substring(0, file.getName().lastIndexOf('.'));
                p.setProjectName(Project.getNewProjectName(projects, fileName));
                projects.add(p);
                p.buildProject(o, this);
                setChanged();
                notifyObservers(p);
            } catch (ClassNotFoundException | IOException e)
            {
                JOptionPane.showMessageDialog(null, "Couldn't load " + file.getName() + System.getProperty("line.separator") + e.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            return p;
        } else
        {
            Project p = new Project(o, this, file);
            projects.add(p);
            setChanged();
            notifyObservers(p);
            return p;
        }
    }

    public Project addProject(BufferedImage image, String name)
    {
        Project p = new Project(o, this, image, name);
        projects.add(p);
        setChanged();
        notifyObservers(p.getImagePanel());
        return p;
    }

    public void reloadPlugins()
    {
        pluginClassLoader = new PluginClassLoader();
        pluginClassLoader.loadDirectory("plugin");
        filters = pluginClassLoader.getClasses();
    }

    public Hashtable<String, IPlugin> getFilters()
    {
        return filters;
    }

    public ArrayList<Project> getProjects()
    {
        return projects;
    }

    private String getFileExtension(File file)
    {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf('.');
        if (lastIndexOf == -1)
            return "";
        return name.substring(lastIndexOf);
    }

    public int getPencilSize()
    {
        return pencilSize;
    }

    public void setPencilSize(int pencilSize)
    {
        this.pencilSize = pencilSize;
    }

    public void setCurrentTool(ToolType tool)
    {
        this.currentTool = tool;
    }

    public ToolType getCurrentTool()
    {
        return currentTool;
    }

    public void setChosenColor(Color color)
    {
        this.chosenColor = color;
    }

    public Color getChosenColor()
    {
        return chosenColor;
    }

    public String getPencilType()
    {
        return pencilType;
    }

    public void setPencilType(String pencilType)
    {
        this.pencilType = pencilType;
    }
}
