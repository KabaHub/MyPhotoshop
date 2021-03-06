package View;

import IHM.ImagePanel;
import IHM.Layer;
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
public class ProjectPane extends CustomJPanel
{
    String projectName;
    Project project;

    JSplitPane jSplitPane;

    ImagePanel imagePanel;
    JScrollPane imageScrollPane;

    JSplitPane infoPanel;
    CustomJPanel histoPanel;
    CustomJPanel layerPanel;
    JScrollPane histoScrollPane;
    JScrollPane layerScrollPane;

    public ProjectPane(Project project)
    {
        super(CustomJPanel.GREY);
        this.setLayout(new BorderLayout());
        this.project = project;
        projectName = project.getProjectName();

        initImagePanel();
        initInfoPanel();
        jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, imageScrollPane, infoPanel);
        this.add(jSplitPane, BorderLayout.CENTER);
        infoPanel.setResizeWeight(0.55f);
        jSplitPane.setResizeWeight(1.00f);
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

        CustomJPanel layersPanel = new CustomJPanel(CustomJPanel.GREY);
        layersPanel.setLayout(new BorderLayout());
        layerPanel = new CustomJPanel(CustomJPanel.BLACK);
        layerPanel.setLayout(new BoxLayout(layerPanel, BoxLayout.PAGE_AXIS));
        layerScrollPane = new JScrollPane(layerPanel);
        layerScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        layersPanel.add(layerScrollPane, BorderLayout.CENTER);

        CustomJPanel layerOptionPanel = new CustomJPanel(CustomJPanel.GREY);
        layerOptionPanel.setLayout(new BoxLayout(layerOptionPanel, BoxLayout.LINE_AXIS));
        JButton addLayer = new JButton("Add Layer");
        addLayer.addActionListener(new LayerOptionButtonListener());
        layerOptionPanel.add(addLayer);
        JButton hideLayer = new JButton("Hide Layer");
        hideLayer.addActionListener(new LayerOptionButtonListener());
        layerOptionPanel.add(hideLayer);
        JButton swapUpLayer = new JButton("^");
        swapUpLayer.addActionListener(new LayerOptionButtonListener());
        layerOptionPanel.add(swapUpLayer);
        JButton swapDownLayer = new JButton("v");
        swapDownLayer.addActionListener(new LayerOptionButtonListener());
        layerOptionPanel.add(swapDownLayer);
        JButton closeLayer = new JButton("X");
        closeLayer.addActionListener(new LayerOptionButtonListener());
        layerOptionPanel.add(closeLayer);

        layersPanel.add(layerOptionPanel, BorderLayout.SOUTH);

        infoPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, histoScrollPane, layersPanel);
    }

    public JSplitPane getInfoPanel()
    {
        return infoPanel;
    }

    public void update()
    {
        if (project.getHistory().size() > 1)
            infoPanel.setVisible(true);
        updateHistory();
        updateLayers();
        imageScrollPane.getViewport().revalidate();
        imageScrollPane.getViewport().repaint();
    }

    protected class HistoButtonListener implements ActionListener
    {
        private int indexOfState;

        public HistoButtonListener(int indexOfState)
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
            newButton.addActionListener(new HistoButtonListener(i));
            newButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, newButton.getMinimumSize().height));
            newButton.setBackground(new Color(60, 60, 60));
            newButton.setForeground(Color.WHITE);
            if (project.getCurrentState() == i)
                newButton.setBackground(new Color(120, 120, 120));
            newButton.setFocusPainted(false);
            histoPanel.add(newButton);
            i++;
        }
        this.revalidate();
        this.repaint();
    }

    protected class LayerButtonListener implements ActionListener
    {
        private int layerPosition;

        public LayerButtonListener(int layerPosition)
        {
            this.layerPosition = layerPosition;
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            project.setCurrentLayer(layerPosition);
        }
    }

    private void updateLayers()
    {
        layerPanel.removeAll();
        JLabel myLabel = new JLabel("Layers", SwingConstants.CENTER);
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        myLabel.setBorder(border);
        myLabel.setForeground(Color.WHITE);
        myLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, myLabel.getMinimumSize().height));
        myLabel.setHorizontalAlignment(JLabel.CENTER);
        myLabel.setVerticalAlignment(JLabel.CENTER);
        layerPanel.add(myLabel);
        for (int j = project.getLayers().size() - 1; j >= 0; j--)
        {
            Layer l = project.getLayers().get(j);
            String layerName = l.getName();
            JButton newButton = new JButton(layerName);
            newButton.addActionListener(new LayerButtonListener(j));
            newButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, newButton.getMinimumSize().height));
            newButton.setBackground(new Color(60, 60, 60));
            newButton.setForeground(new Color(255, 255, 255));
            if (j == project.getCurrentLayer())
                newButton.setBackground(new Color(120, 120, 120));
            if (!project.getLayers().get(j).isVisible())
                newButton.setForeground(new Color(80, 80, 80));
            newButton.setFocusPainted(false);
            layerPanel.add(newButton);
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
        } else
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

    private class LayerOptionButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getActionCommand().contentEquals("Add Layer"))
            {
                project.addLayer();
            } else if (e.getActionCommand().contentEquals("Hide Layer"))
            {
                int l = project.getCurrentLayer();
                project.setLayerVisible(l, !project.getLayerVisible(l));
            } else if (e.getActionCommand().contentEquals("^"))
            {
                project.swapLayerUp(project.getCurrentLayer());
            } else if (e.getActionCommand().contentEquals("v"))
            {
                project.swapLayerDown(project.getCurrentLayer());
            } else if (e.getActionCommand().contentEquals("X"))
            {
                int close = JOptionPane.showConfirmDialog(null, "Are you sure to delete this Layer ?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (close == JOptionPane.OK_OPTION)
                    project.closeLayer(project.getCurrentLayer());
            }
        }
    }
}

