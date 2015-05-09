package entryPoint;

import IHM.ImagePanel;

import javax.swing.*;
import java.io.File;

/**
 * Created by Gabriel on 05/05/2015.
 */
public class CustomSplashScreen extends JFrame
{
    public CustomSplashScreen()
    {
        super();
        setSize(474, 474);
        getContentPane().add(new ImagePanel(new File("ressources/images/splashscreen.png")));
//        try
//        {
//            BufferedImage splash = ImageIO.read(getClass().getResource("../View/ressources/splashscreen.png"));
//            getContentPane().add(new ImagePanel(splash, "SplashScreen"));
//        } catch (IOException e)
//        {
//            e.printStackTrace();
//        }
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
    }
}
