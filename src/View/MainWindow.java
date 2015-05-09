package View;

import Control.Controller;
import IHM.ImagePanel;
import Model.*;
import View.CustomComponents.CustomJPanel;
import entryPoint.CustomSplashScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    private String language = "en";

    public MainWindow(CustomSplashScreen splashScreenToDispose)
    {
        setTitle("MyPhotoshop");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 900);

        menu = new Menu(controller);
        setJMenuBar(menu);
        this.getContentPane().setLayout(new BorderLayout());

        CustomJPanel toolBox = new CustomJPanel(CustomJPanel.BLACK);
        toolBox.setPreferredSize(new Dimension(32, 32));
        this.getContentPane().add(toolBox, BorderLayout.WEST);
        this.getContentPane().add(jTabbedPane, BorderLayout.CENTER);

        // Test
        test();

        setVisible(true);
        splashScreenToDispose.dispose();
    }

    public JTabbedPane getJTabbedPane()
    {
        return jTabbedPane;
    }

    public void addToTabbedPanel(String name, ProjectPane projectPanel)
    {
        jTabbedPane.add(name, projectPanel);

        int index = jTabbedPane.indexOfTab(name);
        JPanel tabNamePanel = new JPanel(new BorderLayout());
        tabNamePanel.setOpaque(false);
        JLabel tabTitle = new JLabel(name);
        JButton closeButton = new CloseButton();
        // Very ugly... without lambdas
        closeButton.addActionListener(e -> jTabbedPane.remove(projectPanel));
        tabNamePanel.add(tabTitle, BorderLayout.CENTER);
        tabNamePanel.add(closeButton, BorderLayout.EAST);
        jTabbedPane.setTabComponentAt(index, tabNamePanel);

        getContentPane().validate();
        getContentPane().repaint();
    }

    public void removeFromTabbedPanel(ProjectPane projectPane)
    {
        jTabbedPane.remove(projectPane);
    }

    public ProjectPane getCurrentTab()
    {
        return (ProjectPane) this.getJTabbedPane().getSelectedComponent();
    }

    public void test()
    {
        model.addProject(new File("ressources/images/Black_kyurem.jpg"));
    }

    @Override
    public void update(Observable o, Object arg)
    {
        if (arg.getClass().getName().contentEquals("Model.Project"))
        {
            Project project = (Project) arg;
            Boolean newProject = true;
            for (int i = 0; i < jTabbedPane.getTabCount(); i++)
            {
                // Existing Project
                ProjectPane pp = (ProjectPane) jTabbedPane.getComponentAt(i);
                if (project == pp.getProject())
                {
                    newProject = false;
                    // Update History
                    pp.update();
                    repaint();
                }
            }
            // New Project (Just Opened)
            if (newProject)
            {
                ProjectPane projectPanel = new ProjectPane(project);
//                if (project.isHistoryEmpty())
//                    projectPanel.getInfoPanel().setVisible(false);
                this.addToTabbedPanel(project.getProjectName(), projectPanel);
                // Set view to the right Tab
                jTabbedPane.setSelectedComponent(projectPanel);
                projectPanel.update();
            }
        }
    }
}
