package View;

import Control.Controller;

import javax.swing.*;

/**
 * Created by duplai_g on 4/22/15.
 */
public class Menu extends JMenuBar
{
    private Controller controller;

    private JMenu file = new JMenu("File");
    private JMenuItem newProject = new JMenuItem("New Project");
    private JMenuItem openProject = new JMenuItem("Open Project");
    private JMenuItem saveProject = new JMenuItem("Save Project");
    private JMenuItem exit = new JMenuItem("Exit");
    private JMenu edit = new JMenu("Edit");
    private JMenuItem undo = new JMenuItem("Undo");
    private JMenuItem redo = new JMenuItem("Redo");

    public Menu(Controller controller)
    {
        this.controller = controller;

        newProject.addActionListener(controller);
        file.add(newProject);
        openProject.addActionListener(controller);
        file.add(openProject);
        saveProject.addActionListener(controller);
        file.add(saveProject);
        exit.addActionListener(controller);
        file.add(exit);
        file.addActionListener(controller);
        add(file);
        undo.addActionListener(controller);
        edit.add(undo);
        redo.addActionListener(controller);
        edit.add(redo);
        add(edit);
    }
}
