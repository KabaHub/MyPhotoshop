package plugin;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by Gabriel on 25/04/2015.
 */
public class PluginClassLoader extends ClassLoader
{
    public Hashtable<String, IPlugin> listPlugin = new Hashtable<>();
    public void loadDirectory(String path)
    {
        File pluginDir = new File(path);
        File[] files = pluginDir.listFiles();
        for (File f : files != null ? files : new File[0])
        {
            if (f.getName().endsWith(".jar"))
            {
                try
                {
                    URLClassLoader ucl = new URLClassLoader(new URL[]{f.toURI().toURL()});
                    JarFile jarFile = new JarFile(f);
                    Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();
                    while (jarEntryEnumeration.hasMoreElements())
                    {
                        JarEntry jarEntry = jarEntryEnumeration.nextElement();
                        String name = jarEntry.getName();
                        if (name.endsWith(".class"))
                        {
                            name = name.replace("/", ".").replace(".class", "");
                            Class c = ucl.loadClass(name);
                            for (Class i : c.getInterfaces())
                            {
                                if (i.getSimpleName().contentEquals("IPlugin"))
                                {
                                    IPlugin plugin = (IPlugin) c.newInstance();
                                    listPlugin.put(plugin.getName(), plugin);
                                }
                            }
                        }
                    }
                    jarFile.close();

                } catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public Hashtable<String, IPlugin> getClasses()
    {
        return listPlugin;
    }

    public static void main(String[] args)
    {
        PluginClassLoader pluginClassLoader = new PluginClassLoader();
        pluginClassLoader.loadDirectory("plugin");
        Hashtable<String, IPlugin> h = pluginClassLoader.getClasses();
        System.out.println(h);
    }
}
