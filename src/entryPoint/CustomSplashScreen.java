package entryPoint;

import IHM.ImagePanel;
import jdk.nashorn.internal.ir.RuntimeNode;
import sun.misc.Request;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Gabriel on 05/05/2015.
 */
public class CustomSplashScreen extends JFrame
{
    public CustomSplashScreen(boolean splash)
    {
        super();
        setSize(474, 474);
        if (!splash)
            setSize(490, 520);
        getContentPane().add(new ImagePanel(new File("asset/splashscreen.png")));
//        try
//        {
//            BufferedImage splash = ImageIO.read(getClass().getResource("../../asset/splashscreen.png"));
//            getContentPane().add(new ImagePanel(splash, "SplashScreen"));
//        } catch (IOException e)
//        {
//            e.printStackTrace();
//        }
        setLocationRelativeTo(null);
        if (splash)
        {
            setUndecorated(true);
        }
        setVisible(true);
    }
}
