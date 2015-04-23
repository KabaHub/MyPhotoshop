package entryPoint;

import View.MainWindow;

import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;

public class Main
{
    public static void main(String[] args)
    {
        MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        MainWindow mainWindow = new MainWindow();
    }
}
