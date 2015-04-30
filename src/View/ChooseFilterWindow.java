package View;

import Control.PluginExecutor;
import IHM.ImagePanel;
import Model.Project;
import plugin.IPlugin;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by Gabriel on 27/04/2015.
 */
public class ChooseFilterWindow extends JFrame
{
    private Project project;
    private Hashtable<String, IPlugin> filters;
    private JComboBox<String> combo;
    private String selectedFilter;

    public ChooseFilterWindow(Hashtable<String, IPlugin> filters, Project project)
    {
        this.filters = filters;
        this.project = project;

        setTitle("Filters");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 150);

        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        JButton okButton = new JButton("Apply");
        okButton.addActionListener(new ButtonListener(this));
        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(new ButtonListener(this));
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ButtonListener(this));

        combo = new JComboBox<>();
        Enumeration filterName = filters.keys();
        ArrayList<String> sortedFilterName = new ArrayList<>();
        while (filterName.hasMoreElements())
            sortedFilterName.add((String)filterName.nextElement());
        Collections.sort(sortedFilterName);
        sortedFilterName.forEach(combo::addItem);
        combo.addItemListener(new ItemState());
        combo.setSelectedIndex(0);
        selectedFilter = combo.getSelectedItem().toString();
        JLabel label = new JLabel("Select Filter");
        JPanel top = new JPanel();
        top.add(label);
        top.add(combo);

        JPanel down = new JPanel();
        down.setLayout(new BoxLayout(down, BoxLayout.LINE_AXIS));
        down.add(okButton);
        down.add(stopButton);
        down.add(cancelButton);

        this.getContentPane().add(top);
        this.getContentPane().add(down);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private class ItemState implements ItemListener
    {
        @Override
        public void itemStateChanged(ItemEvent e)
        {
            selectedFilter = e.getItem().toString();
        }
    }

    public class ButtonListener implements ActionListener
    {
        ChooseFilterWindow chooseFilterWindow;
        public ButtonListener(ChooseFilterWindow chooseFilterWindow)
        {
            super();
            this.chooseFilterWindow = chooseFilterWindow;
        }
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getActionCommand().contentEquals("Apply"))
            {
                IPlugin plugin = filters.get(selectedFilter);
                if (plugin != null)
                {
                    if (project.isPluginRunning())
                    {
                        JOptionPane info = new JOptionPane();
                        JOptionPane.showMessageDialog(null, "A filter is already Running", "Warning", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else
                    project.applyPlugin(plugin);
                }
            } else if (e.getActionCommand().contentEquals("Cancel"))
            {
                chooseFilterWindow.dispose();
            } else if (e.getActionCommand().contentEquals("Stop"))
            {
                project.stopPlugin();
            }
        }

        @Deprecated
        private BufferedImage executeFilter(BufferedImage image, String filterName)
        {
            IPlugin plugin = filters.get(filterName);
            if (plugin != null)
            {
                BufferedImage result = plugin.perform(image);
                return result;
            } else
            {
                System.out.println("image null");
                return null;
            }
        }

        private BufferedImage test()
        {
            BufferedImage test = null;
            try
            {
                File file = new File("ressources/images/lena.jpg");
                test = ImageIO.read(file);
                return test;
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            return test;
        }
    }
}