package View;

import Control.Controller;
import IHM.ImagePanel;
import Model.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by duplai_g on 4/21/15.
 */
public class MainWindow extends JFrame implements Observer
{
    private Menu menu;
    private Model model = new Model(this);
    private Controller controller = new Controller(this, model);

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

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        getContentPane().add(topPanel);
        topPanel.add(jTabbedPane, BorderLayout.CENTER);

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

    public ImagePanel getCurrentTab()
    {
        return (ImagePanel)this.getjTabbedPane().getSelectedComponent();
    }

    @Override
    public void update(Observable o, Object arg)
    {
        System.out.println(arg.getClass().getName());
        if (arg.getClass().getName().contentEquals("Model.Project"))
        {
            Project newProject = (Project)arg;
            addToTabbedPanel(newProject.getProjectName(), newProject.getImagePanel());
        }
    }
}
