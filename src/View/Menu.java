package View;

import javax.swing.*;

/**
 * Created by duplai_g on 4/22/15.
 */
public class Menu extends JMenuBar
{
    private JMenu file = new JMenu("File");
    private JMenu edit = new JMenu("Edit");

    public Menu()
    {
        add(file);
        add(edit);
    }
}
