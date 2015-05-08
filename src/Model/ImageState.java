package Model;


import IHM.Layer;

import javax.imageio.ImageIO;
import java.awt.*;
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
    private ArrayList<Layer> layers = new ArrayList<>();
    private Date date;
    private int currentLayer;

    public ImageState(String appliedIPlugin, ArrayList<Layer> layers, int currentLayer)
    {
        this.appliedIPlugin = appliedIPlugin;
        for (Layer l : layers)
        {
            BufferedImage exImage = l.getImage();
            BufferedImage newImage = new BufferedImage(exImage.getWidth(), exImage.getHeight(), exImage.getType());
            Graphics g = newImage.getGraphics();
            g.drawImage(exImage, 0, 0, null);
            g.dispose();
            this.layers.add(new Layer(l.getName(), newImage));
        }
//        this.layers.addAll(layers.stream().map(l -> new Layer(l.getName(), l.getImage())).collect(Collectors.toList()));
        date = Calendar.getInstance().getTime();
        prepareToSerialization();
        this.currentLayer = currentLayer;
    }

    public void buildImage()
    {
        layers.forEach(IHM.Layer::buildImage);
    }

    public void prepareToSerialization()
    {
        layers.forEach(IHM.Layer::prepareToSerialization);
    }

    public int getCurrentLayer()
    {
        return currentLayer;
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
