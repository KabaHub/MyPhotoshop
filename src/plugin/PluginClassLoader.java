package plugin;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
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
        File[] files = null;
        File dirToScan = new File(path);
        files = dirToScan.listFiles();
        assert files != null;
        for (File f : files)
        {
            if (f.getName().endsWith(".jar"))
            {
                load(f);
            }
        }
    }

    private void load(File file)
    {
        try
        {
            URL toURL = file.toURI().toURL();
            URLClassLoader ucl = new URLClassLoader(new URL[]{toURL});
            JarFile jf = new JarFile(file);
            Enumeration<JarEntry> eje = jf.entries();
            while (eje.hasMoreElements())
            {
                JarEntry je = eje.nextElement();
                String name = je.getName();
                if (je.getName().endsWith(".class"))
                {
                    name = name.replace("/", ".").replace(".class", "");
                    Class c = ucl.loadClass(name);
                    Class[] interfaces = c.getInterfaces();
                    for (Class i : interfaces)
                    {
                        if (i.getSimpleName().equals("IPlugin"))
                        {
                            IPlugin ip = (IPlugin) c.newInstance();
                            listPlugin.put(ip.getName(), ip);
                        }
                    }
                }
            }
            jf.close();
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException | IOException e)
        {
            e.printStackTrace();
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
