package View;

import IHM.ImagePanel;
import Model.ImageState;
import Model.Project;
import View.CustomComponents.CustomJPanel;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

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
    CustomJPanel histoPanel;
    JScrollPane histoScrollPane;

    public ProjectPane(Project project)
    {
        this.setLayout(new BorderLayout());
        this.project = project;
        projectName = project.getProjectName();

        initImagePanel();
        initInfoPanel();
        jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, imageScrollPane, infoPanel);
        this.add(jSplitPane, BorderLayout.CENTER);
        infoPanel.setResizeWeight(0.60f);
        jSplitPane.setResizeWeight(0.80f);
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
        histoPanel = new CustomJPanel(CustomJPanel.BLACK);
        histoPanel.setLayout(new BoxLayout(histoPanel, BoxLayout.PAGE_AXIS));
        histoScrollPane = new JScrollPane(histoPanel);
        histoScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        CustomJPanel tmp = new CustomJPanel(CustomJPanel.BLACK);
        infoPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, histoScrollPane, tmp);
    }

    public JSplitPane getInfoPanel()
    {
        return infoPanel;
    }

    public void updateInfoPanel()
    {
        if (project.getHistory().size() > 1)
            infoPanel.setVisible(true);
        updateHistory();
    }

    protected class ButtonListener implements ActionListener
    {
        private int indexOfState;
        public ButtonListener(int indexOfState)
        {
            this.indexOfState = indexOfState;
        }
        @Override
        public void actionPerformed(ActionEvent e)
        {
            project.setCurrentState(indexOfState);
        }
    }

    private void updateHistory()
    {
        histoPanel.removeAll();
        int i = 0;
        JLabel myLabel = new JLabel("History", SwingConstants.CENTER);
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        myLabel.setBorder(border);
        myLabel.setForeground(Color.WHITE);
        myLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, myLabel.getMinimumSize().height));
        myLabel.setHorizontalAlignment(JLabel.CENTER);
        myLabel.setVerticalAlignment(JLabel.CENTER);
        histoPanel.add(myLabel);
        for (ImageState imageState : project.getHistory())
        {
            String appliedIPlugin = imageState.getAppliedIPlugin();
            JButton newButton = new JButton(appliedIPlugin);
            newButton.addActionListener(new ButtonListener(i));
            newButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, newButton.getMinimumSize().height));
            newButton.setBackground(new Color(80, 80, 80));
            if (project.getCurrentState() == i)
                newButton.setBackground(new Color(100, 100, 100));
            newButton.setForeground(Color.WHITE);
            newButton.setFocusPainted(false);
//            newButton.setContentAreaFilled(false);
//            newButton.setBorder(border);
            histoPanel.add(newButton);
            i++;
        }
        this.revalidate();
        this.repaint();
    }

    public void setInfoPanelVisibility(boolean b)
    {
        if (!b)
        {
            jSplitPane.setDividerSize(0);
            jSplitPane.setDividerLocation(infoPanel.getLocation().x + infoPanel.getSize().width);
            infoPanel.setVisible(false);
        }
        else
        {
            jSplitPane.setDividerSize(5);
            jSplitPane.resetToPreferredSizes();
            infoPanel.setVisible(true);
        }
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

