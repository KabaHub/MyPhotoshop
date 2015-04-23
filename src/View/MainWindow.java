package View;

import Control.Controller;
import IHM.ImagePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by duplai_g on 4/21/15.
 */
public class MainWindow extends JFrame
{
    private Menu menu;
    private Controller controller = new Controller();

    public MainWindow()
    {
        //testImagePanel();

        setTitle("MyPhotoshop");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        menu = new Menu(controller);
        setJMenuBar(menu);

        setVisible(true);
    }

    private void testImagePanel()
    {
        JPanel panel;
        BufferedImage bufferedImage = null;
        //
        try
        {
            bufferedImage = ImageIO.read(new File("ressources/images/test.png"));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        panel = new ImagePanel(bufferedImage, "Banana !");
        setContentPane(panel);
    }
}
