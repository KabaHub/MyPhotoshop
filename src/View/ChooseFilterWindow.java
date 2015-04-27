package View;

import IHM.ImagePanel;
import Model.Project;
import plugin.IPlugin;

import javax.imageio.ImageIO;
import javax.swing.*;
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
    private JComboBox combo;
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
        okButton.addActionListener(new ButtonListener());
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ButtonListener());
        combo = new JComboBox();
        Enumeration filterName = filters.keys();
        while (filterName.hasMoreElements())
            combo.addItem(filterName.nextElement());
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
        down.add(cancelButton);

        this.getContentPane().add(top);
        this.getContentPane().add(down);

        this.setVisible(true);
    }

    class ItemState implements ItemListener
    {
        @Override
        public void itemStateChanged(ItemEvent e)
        {
            selectedFilter = e.getItem().toString();
        }
    }

    class ButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getActionCommand().contentEquals("Apply"))
            {
                ImagePanel imagePanel = project.getImagePanel();
                BufferedImage image = imagePanel.getImage();
                image = test();
                project.setImagePanel(image);
            }
            else if (e.getActionCommand().contentEquals("Cancel"))
            {

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
            return  test;
        }
    }
}