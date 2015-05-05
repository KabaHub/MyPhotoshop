package Model;


import IHM.Layer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Created by Gabriel on 24/04/2015.
 */
public class ImageState implements Serializable
{
    private String appliedIPlugin;
//    private transient BufferedImage image;
    private ArrayList<Layer> layers = new ArrayList<>();
//    private byte[] imageByte;
    Date date;

    public ImageState(String appliedIPlugin, ArrayList<Layer> layers)
    {
        this.appliedIPlugin = appliedIPlugin;
        this.layers.addAll(layers.stream().map(l -> new Layer(l.getName(), l.getImage())).collect(Collectors.toList()));
        date = Calendar.getInstance().getTime();
        prepareToSerialization();
    }

    public void buildImage()
    {
        for (Layer l : layers)
            l.buildImage();
    }

    public void prepareToSerialization()
    {
        for (Layer l : layers)
            l.prepareToSerialization();
    }

    public ArrayList<Layer> getLayers()
    {
        return layers;
    }
    public String getAppliedIPlugin()
    {
        return appliedIPlugin;
    }
}
