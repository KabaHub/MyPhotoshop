package Control;

import IHM.ImagePanel;
import View.MainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by duplai_g on 4/22/15.
 */
public class Controller implements ActionListener
{
    MainWindow mainWindow;
    public Controller(MainWindow mainWindow)
    {
        this.mainWindow = mainWindow;
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().contentEquals("File"))
        {
        }
        else if (e.getActionCommand().contentEquals("New Project"))
        {
            mainWindow.testImagePanel();
        }
        else if (e.getActionCommand().contentEquals("Open Project"))
        {

        }
        else if (e.getActionCommand().contentEquals("Save Project"))
        {
            System.out.println(((ImagePanel)mainWindow.getjTabbedPane().getSelectedComponent()).getFileName());
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
        else
            System.out.println("Not yet Implemented : " + e.getActionCommand());
    }
}
