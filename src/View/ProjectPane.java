package View;

import IHM.ImagePanel;
import Model.ImageState;
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

    JSplitPane jSplitPane;

    ImagePanel imagePanel;
    JScrollPane imageScrollPane;

    JSplitPane infoPanel;
    JPanel histoPanel;
    ArrayList<JButton> histoButtons = new ArrayList<>();
    JScrollPane histoScrollPane;

    public ProjectPane(Project project)
    {
        this.setLayout(new BorderLayout());
        this.project = project;
        projectName = project.getProjectName();

        initImagePanel();
        initInfoPanel();
        jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, imageScrollPane, infoPanel);
        jSplitPane.setResizeWeight(1.0f);
        this.add(jSplitPane, BorderLayout.CENTER);
    }

    private void initImagePanel()
    {
        imagePanel = project.getImagePanel();
        imageScrollPane = new JScrollPane(imagePanel);
        imageScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        imageScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }

    private void initInfoPanel()
    {
        histoPanel = new JPanel(new BoxLayout(null, BoxLayout.PAGE_AXIS));
        histoPanel.setPreferredSize(new Dimension(300, 300));
        histoScrollPane = new JScrollPane(histoPanel);
        JPanel tmp = new JPanel();
        infoPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, histoScrollPane, tmp);

        histoScrollPane = new JScrollPane(histoPanel);
    }

    public JSplitPane getInfoPanel()
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

