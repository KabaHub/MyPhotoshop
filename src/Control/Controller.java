package Control;

import View.ChooseFilterWindow;
import View.MainWindow;
import Model.Model;
import Model.Project;
import Model.ImageState;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Created by duplai_g on 4/22/15.
 */
public class Controller implements ActionListener
{
    private Model model;
    private MainWindow mainWindow;
    private JFileChooser projectFileChooser = new JFileChooser(System.getProperty("user.dir"));
    private JFileChooser imageFileChooser = new JFileChooser(System.getProperty("user.dir"));
    private JFileChooser saveFileChooser = new JFileChooser(System.getProperty("user.dir"));

    public Controller(MainWindow mainWindow, Model model)
    {
        super();
        this.mainWindow = mainWindow;
        this.model = model;
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().contentEquals("File"))
        {
        } else if (e.getActionCommand().contentEquals("New Project"))
        {
            model.addProject();
        } else if (e.getActionCommand().contentEquals("Open Project"))
        {
            if (projectFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                File f = projectFileChooser.getSelectedFile();
                model.addProject(f);
            }
        } else if (e.getActionCommand().contentEquals("Import Image"))
        {
            if (imageFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                File f = imageFileChooser.getSelectedFile();
                mainWindow.getCurrentTab().getProject().importImage(f);
            }
        } else if (e.getActionCommand().contentEquals("Save Project"))
        {
            System.out.println(mainWindow.getCurrentTab().getProjectName());
            if (saveFileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                Project p = mainWindow.getCurrentTab().getProject();
                File f = saveFileChooser.getSelectedFile();
                try
                {
                    p.prepareToSerialization();
                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
                    oos.writeObject(p);
                } catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }

        } else if (e.getActionCommand().contentEquals("Close Project"))
        {
            mainWindow.removeFromTabbedPanel(mainWindow.getCurrentTab());
        } else if (e.getActionCommand().contentEquals("Exit"))
        {
            System.exit(0);
        } else if (e.getActionCommand().contentEquals("Undo"))
        {

        } else if (e.getActionCommand().contentEquals("Redo"))
        {

        } else if (e.getActionCommand().contentEquals("Hide Project Toolbar"))
        {
            if (mainWindow.getCurrentTab().getInfoPanel().isVisible())
                mainWindow.getCurrentTab().getInfoPanel().setVisible(false);
            else
                mainWindow.getCurrentTab().getInfoPanel().setVisible(true);
        } else if (e.getActionCommand().contentEquals("Apply Filter"))
        {
            new ChooseFilterWindow(model.getFilters(), mainWindow.getCurrentTab().getProject());
        } else if (e.getActionCommand().contentEquals("Reload Filters"))
        {
            model.reloadPlugins();
        } else if (e.getActionCommand().contentEquals("Test"))
        {
            System.out.println("History :");
            System.out.println("--------------------------");
            for (ImageState i : mainWindow.getCurrentTab().getProject().getHistory())
                System.out.println(i.getAppliedIPlugin());
        } else if (e.getActionCommand().contentEquals("CloseTab"))
        {
            mainWindow.removeFromTabbedPanel(mainWindow.getCurrentTab());
        } else
            System.out.println(e.getActionCommand() + " not yet Implemented : " + e.getActionCommand());
    }
}
