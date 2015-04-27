package Control;

import View.ChooseFilterWindow;
import View.MainWindow;
import Model.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by duplai_g on 4/22/15.
 */
public class Controller implements ActionListener
{
    private Model model;
    private MainWindow mainWindow;
    private JFileChooser projectFileChooser = new JFileChooser(System.getProperty("user.dir"));
    private JFileChooser imageFileChooser = new JFileChooser(System.getProperty("user.dir"));

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
        }
        else if (e.getActionCommand().contentEquals("New Project"))
        {
            model.addProject();
        }
        else if (e.getActionCommand().contentEquals("Open Project"))
        {
            if (projectFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                File f = projectFileChooser.getSelectedFile();
                System.out.println(f.getName());
            }
        }
        else if (e.getActionCommand().contentEquals("Import Image"))
        {
            if (imageFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                File f = imageFileChooser.getSelectedFile();
                model.addProject(f);
            }
        }
        else if (e.getActionCommand().contentEquals("Save Project"))
        {
            System.out.println(mainWindow.getCurrentTab().getProjectName());
        }
        else if (e.getActionCommand().contentEquals("Exit"))
        {
            System.exit(0);
        }
        else if (e.getActionCommand().contentEquals("Undo"))
        {

        }
        else if (e.getActionCommand().contentEquals("Redo"))
        {

        }
        else if (e.getActionCommand().contentEquals("Hide Project Toolbar"))
        {
            if (mainWindow.getCurrentTab().getInfoPanel().isVisible())
                mainWindow.getCurrentTab().getInfoPanel().setVisible(false);
            else
                mainWindow.getCurrentTab().getInfoPanel().setVisible(true);
        }
        else if (e.getActionCommand().contentEquals("Apply Filter"))
        {
            ChooseFilterWindow chooseFilterWindow = new ChooseFilterWindow(model.getFilters());
        }
        else
            System.out.println("Not yet Implemented : " + e.getActionCommand());
    }
}
