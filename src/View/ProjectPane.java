package View;

import IHM.ImagePanel;
import Model.Project;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Gabriel on 24/04/2015.
 */
public class ProjectPane extends JPanel
{
    String projectName;
    Project project;

    ImagePanel imagePanel;
    JScrollPane imageScrollPane;

    JPanel infoPanel;
    JPanel histoPanel;
    ArrayList<JButton> histoButtons;
    JScrollPane histoScrollPane;

    public ProjectPane(Project project)
    {
        this.setLayout(new BorderLayout());
        this.project = project;
        projectName = project.getProjectName();

        initImagePanel();
        initInfoPanel();
    }

    private void initImagePanel()
    {
        imagePanel = project.getImagePanel();
        imageScrollPane = new JScrollPane(imagePanel);
        imageScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        imageScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(imageScrollPane, BorderLayout.CENTER);
    }

    private void initInfoPanel()
    {
        infoPanel = new JPanel();

        histoPanel = new JPanel();
        histoPanel.setLayout(new BoxLayout(histoPanel, BoxLayout.PAGE_AXIS));

//        JButton button1 = new JButton("Test");
//        button1.setMaximumSize(new Dimension(300, 300));
//        histoPanel.add(button1);
        histoPanel.setPreferredSize(new Dimension(300, 300));

        histoScrollPane = new JScrollPane(histoPanel);
        infoPanel.add(histoScrollPane);

        this.add(infoPanel, BorderLayout.EAST);
    }

    public JPanel getInfoPanel()
    {
        return infoPanel;
    }

    public Project getProject()
    {
        return project;
    }

    public String getProjectName()
    {
        return projectName;
    }
}

