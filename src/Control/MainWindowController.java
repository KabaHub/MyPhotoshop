package Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by duplai_g on 4/22/15.
 */
public class MainWindowController implements ActionListener
{
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().compareTo("Toast") == 0)
        {
            System.out.println("CACAAAAA");
        }
        else
            System.out.println("loul");
    }
}
