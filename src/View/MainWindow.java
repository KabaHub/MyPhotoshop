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

    private String language = "en";

    public MainWindow()
    {
        setTitle("MyPhotoshop");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        menu = new Menu(controller);
        setJMenuBar(menu);
        this.getContentPane().setLayout(new BorderLayout());

        JPanel toolBox = new JPanel();
        toolBox.setPreferredSize(new Dimension(32, 32));
        this.getContentPane().add(toolBox, BorderLayout.WEST);
        this.getContentPane().add(jTabbedPane, BorderLayout.CENTER);

        // Test
        test();

        setVisible(true);
    }

    public JTabbedPane getJTabbedPane()
    {
        return jTabbedPane;
    }

    public void addToTabbedPanel(String name, ProjectPane projectPanel)
    {
        jTabbedPane.add(name, projectPanel);

//        int index = jTabbedPane.indexOfTab(name);
//        JPanel tabNamePanel = new JPanel(new BorderLayout());
//        tabNamePanel.setOpaque(false);
//        JLabel tabTitle = new JLabel("New Project");
//        ImageIcon closeImage = new ImageIcon("ressources/images/close-icon.png");
//        JButton closeButton = new JButton(closeImage);
//        closeButton.setMaximumSize(new Dimension(12, 12));
//        closeButton.setPreferredSize(new Dimension(12, 12));
//        closeButton.setToolTipText("Close this Project");
//        closeButton.addActionListener(controller);
//        tabNamePanel.add(tabTitle, BorderLayout.CENTER);
//        tabNamePanel.add(closeButton, BorderLayout.EAST);
//        jTabbedPane.setTabComponentAt(index, tabNamePanel);

        getContentPane().validate();
        getContentPane().repaint();
    }

    public ProjectPane getCurrentTab()
    {
        return (ProjectPane) this.getJTabbedPane().getSelectedComponent();
    }

    public void test()
    {
        model.addProject(new File("ressources/images/DarkKnight.jpg"));
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
                ProjectPane pp = (ProjectPane) jTabbedPane.getComponentAt(i);
                if (project == pp.getProject())
                {
                    newProject = false;
                    repaint();
                }
            }
            if (newProject)
            {
                ProjectPane projectPanel = new ProjectPane(project);
                if (project.isHistoryEmpty())
                    projectPanel.getInfoPanel().setVisible(false);
                this.addToTabbedPanel(project.getProjectName(), projectPanel);
            }
        }
    }
}
