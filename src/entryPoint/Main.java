package entryPoint;

import View.MainWindow;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;
import java.awt.*;

public class Main
{
    public static void main(String[] args)
    {
        Boolean whiteLookAndFeel = false;
        CustomSplashScreen customSplashScreen = new CustomSplashScreen(true);
        String nimbusName = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
        for (UIManager.LookAndFeelInfo l : UIManager.getInstalledLookAndFeels())
        {
            if (l.getClassName().contentEquals(nimbusName))
            {
                try
                {
                    UIManager.setLookAndFeel(nimbusName);
                    whiteLookAndFeel = true;
                } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e)
                {
                    e.printStackTrace();
                }
            }
        }
//        MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        MainWindow mainWindow = new MainWindow(customSplashScreen, whiteLookAndFeel);
    }
}
