package View;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Gabriel on 30/04/2015.
 */
public class ImageFileFilter extends FileFilter
{
    protected String description;
    protected ArrayList<String> extensions = new ArrayList<>();
    private String format = null;
    

    public ImageFileFilter(String description, String extension)
    {
        this.description = description;
        this.extensions.add(extension);
    }

    public ImageFileFilter(String description, String extension, String format)
    {
        this.description = description;
        this.extensions.add(extension);
        this.format = format;
    }

    public ImageFileFilter(String description, String[] extensions)
    {
        this.description = description;
        Collections.addAll(this.extensions, extensions);
    }

    public ImageFileFilter(String description, String[] extensions, String format)
    {
        this.description = description;
        Collections.addAll(this.extensions, extensions);
        this.format = format;
    }

    public String getExtension()
    {
        return extensions.get(0);
    }

    public String getFormat()
    {
        return format;
    }

    @Override
    public boolean accept(File f)
    {
        if (f.isDirectory())
            return true;
        else
        {
            String path = f.getAbsolutePath().toLowerCase();
            for (String extension : extensions)
            {
                if (path.endsWith(extension))
                    return true;
            }
        }
        return false;
    }

    @Override
    public String getDescription()
    {
        return description;
    }
}
