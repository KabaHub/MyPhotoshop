package View;

import Control.Controller;
import Model.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by duplai_g on 4/21/15.
 */
public class MainWindow extends JFrame
{
    private Menu menu;
    private Controller controller = new Controller(this);


    private JTabbedPane jTabbedPane = new JTabbedPane();

    // test
    private JPanel panel1;

    public MainWindow()
    {
        setTitle("MyPhotoshop");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        menu = new Menu(controller);
        setJMenuBar(menu);

        //test
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        getContentPane().add(topPanel);
        topPanel.add(jTabbedPane, BorderLayout.CENTER);
        // end test

        setVisible(true);
    }

    public JTabbedPane getjTabbedPane()
    {
        return jTabbedPane;
    }

    public void addToTabbedPanel(String name, JPanel panel)
    {
        jTabbedPane.add(name, panel);
    }

    public void testImagePanel()
    {
        File f = new File("ressources/images/test.png");
        Model m = new Model();
        Project p = m.addProject(f);
        addToTabbedPanel("Banana!", p.getImagePanel());
    }
}
