package Control;

import Model.Project;
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
        try
        {
            BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
            Graphics g = result.getGraphics();
            g.drawImage(image, 0, 0, null);
            g.dispose();
            Thread.sleep(0);
            result = plugin.perform(result);
            if (result != null)
                project.setImageChanges(result, plugin.getName());
            project.setPluginRunning(false);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
    }
}
