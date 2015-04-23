package Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by duplai_g on 4/22/15.
 */
public class Controller implements ActionListener
{
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().contentEquals("File"))
        {
        }
        else if (e.getActionCommand().contentEquals("New Project"))
        {

        }
        else if (e.getActionCommand().contentEquals("Open Project"))
        {

        }
        else if (e.getActionCommand().contentEquals("Save Project"))
        {

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
