package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Created by duplai_g on 4/21/15.
 */
public class MainWindow extends JFrame
{
    private JMenuBar menuBar;

    public MainWindow()
    {
        JButton button1 = new JButton("Button 1");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(button1);

        setContentPane(panel);
        setTitle("MyPhotoshop");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);
    }
}
