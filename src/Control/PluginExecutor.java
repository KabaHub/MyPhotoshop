package Control;

import Model.Project;
import View.ChooseFilterWindow;
import plugin.IPlugin;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Gabriel on 29/04/2015.
 */
public class PluginExecutor implements Runnable
{
    private Project project;
    private BufferedImage image;
    private IPlugin plugin;

    public PluginExecutor(Project project, BufferedImage image, IPlugin plugin)
    {
        this.project = project;
        this.image = image;
        this.plugin = plugin;
    }

    @Override
    public void run()
    {
        BufferedImage result = plugin.perform(image);
        if (result != null)
            project.setImageChanges(result, plugin.getName());
        project.setPluginRunning(false);
    }
}
