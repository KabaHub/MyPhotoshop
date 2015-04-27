package Model;

import plugin.IPlugin;
import plugin.PluginClassLoader;

import java.awt.image.BufferedImage;
import java.io.File;
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

    public Model(Observer o)
    {
        this.o = o;
        addObserver(o);
        pluginClassLoader.loadDirectory("plugin");
        filters = pluginClassLoader.getClasses();
    }

    public Project addProject()
    {
        Project p = new Project(o);
        projects.add(p);
        setChanged();
        notifyObservers(p);
        return p;
    }

    public Project addProject(File file)
    {
        Project p = new Project(o, file);
        projects.add(p);
        setChanged();
        notifyObservers(p);
        return p;
    }

    public Project addProject(BufferedImage image, String name)
    {
        Project p = new Project(o, image, name);
        projects.add(p);
        setChanged();
        notifyObservers(p.getImagePanel());
        return p;
    }

    public Hashtable<String, IPlugin> getFilters()
    {
        return filters;
    }
}
