package View;

import Control.Controller;
import Model.*;
import View.CustomComponents.*;
import entryPoint.CustomSplashScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by duplai_g on 04/21/15.
 */
public class MainWindow extends JFrame implements Observer
{
    private Menu menu;
    private Model model = new Model(this);
    // If the LookAndFeel is White.
    private boolean whiteLookAndFeel;
    private Controller controller = new Controller(this, model);
    private JTabbedPane jTabbedPane = new JTabbedPane();

//    private CustomJPanel toolBox = new CustomJPanel(CustomJPanel.GREY);
    private JToolBar toolBox = new JToolBar(JToolBar.VERTICAL);
    private ChooseColorButton chooseColorButton;

    private String language = "en";

    public MainWindow(CustomSplashScreen splashScreenToDispose, boolean whiteLookAndFeel)
    {
        this.whiteLookAndFeel = whiteLookAndFeel;
        setTitle("MyPhotoshop");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1200, 900);

        menu = new Menu(controller);
        setJMenuBar(menu);
        this.getContentPane().setLayout(new BorderLayout());

        initToolBox();
        this.getContentPane().add(toolBox, BorderLayout.WEST);
        this.getContentPane().add(jTabbedPane, BorderLayout.CENTER);

        setVisible(true);
        splashScreenToDispose.dispose();
    }

    private void initToolBox()
    {
        toolBox.setPreferredSize(new Dimension(32, 32));
        toolBox.setBackground(new Color(80, 80, 80));
//        toolBox.setFloatable(false);
        toolBox.setRollover(true);
        toolBox.setMinimumSize(new Dimension(40, 40));
        toolBox.setPreferredSize(new Dimension(35, 35));
        toolBox.setBorderPainted(false);

//        toolBox.addSeparator(new Dimension(30,30));
        ToolButton moveLayerButton = ToolButton.getNewButton(model, ToolButton.MOVE_LAYER_TOOL, whiteLookAndFeel);
        toolBox.add(moveLayerButton);
        toolBox.addSeparator();
        ToolButton pencilButton = ToolButton.getNewButton(model, ToolButton.PENCIL_TOOL, whiteLookAndFeel);
        toolBox.add(pencilButton);
        toolBox.addSeparator();
        ToolButton brushButton = ToolButton.getNewButton(model, ToolButton.BRUSH_TOOL, whiteLookAndFeel);
        toolBox.add(brushButton);
        toolBox.addSeparator();
        ToolButton eraserButton = ToolButton.getNewButton(model, ToolButton.ERASER_TOOL, whiteLookAndFeel);
        toolBox.add(eraserButton);
        toolBox.addSeparator();
        ToolButton eyeDropperButton = ToolButton.getNewButton(model, ToolButton.EYEDROPPER_TOOL, whiteLookAndFeel);
        toolBox.add(eyeDropperButton);
        toolBox.addSeparator();
        toolBox.addSeparator();
        chooseColorButton = new ChooseColorButton(model);
        toolBox.add(chooseColorButton);
        toolBox.addSeparator();
        ChoosePencilSizeButton choosePencilSizeButton = new ChoosePencilSizeButton(model, whiteLookAndFeel);
        toolBox.add(choosePencilSizeButton);
        toolBox.addSeparator();
        ChoosePencilTypeButton choosePencilTypeButton = new ChoosePencilTypeButton(model, whiteLookAndFeel);
        toolBox.add(choosePencilTypeButton);
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
        CloseButton closeButton = new CloseButton();
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
        model.addProject(new File("test/images/Black_kyurem.jpg"));
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
        else if (arg.getClass().getName().contentEquals("java.awt.Color"))
        {
            chooseColorButton.update();
            chooseColorButton.repaint();
        }
    }
}
