package View;

import Control.Controller;
import Model.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by duplai_g on 4/21/15.
 */
public class MainWindow extends JFrame
{
    private Menu menu;
    private Controller controller = new Controller(this);
    private JTabbedPane jTabbedPane = new JTabbedPane();

    // test
    private JPanel panel1;

    public MainWindow()
    {
        setTitle("MyPhotoshop");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        menu = new Menu(controller);
        setJMenuBar(menu);

        //test
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        getContentPane().add(topPanel);
        topPanel.add(jTabbedPane, BorderLayout.CENTER);
        // end test

        setVisible(true);
    }

    public void addToTabbedPanel(String name, JPanel panel)
    {
        jTabbedPane.add(name, panel);
    }

    public void testImagePanel()
    {
        System.out.println("test");
        File f = new File("ressources/images/test.png");
        Model m = new Model();
        Project p = m.addProject(f);
        addToTabbedPanel("Banana!", p.getImagePanel());
    }

    public void createPage1()
    {
        panel1 = new JPanel();
        panel1.setLayout( null );

        JLabel label1 = new JLabel( "Username:" );
        label1.setBounds( 10, 15, 150, 20 );
        panel1.add( label1 );

        JTextField field = new JTextField();
        field.setBounds( 10, 35, 150, 20 );
        panel1.add( field );

        JLabel label2 = new JLabel( "Password:" );
        label2.setBounds( 10, 60, 150, 20 );
        panel1.add( label2 );

        JPasswordField fieldPass = new JPasswordField();
        fieldPass.setBounds( 10, 80, 150, 20 );
        panel1.add( fieldPass );
    }
}
