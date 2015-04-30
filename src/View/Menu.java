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
    private JMenuItem importImage = new JMenuItem("Import Image");
    private JMenuItem saveProject = new JMenuItem("Save Project");
    private JMenuItem closeProject = new JMenuItem("Close Project");
    private JMenuItem exit = new JMenuItem("Exit");

    private JMenu edit = new JMenu("Edit");
    private JMenuItem undo = new JMenuItem("Undo");
    private JMenuItem redo = new JMenuItem("Redo");

    private JMenu view = new JMenu("View");
    private JMenuItem hideInfo = new JMenuItem("Hide Project Toolbar");

    private JMenu filter = new JMenu("Filter");
    private JMenuItem apply = new JMenuItem("Apply Filter");
    private JMenuItem reload = new JMenuItem("Reload Filters");

    private JMenu test = new JMenu("Test");
    private JMenuItem testitem = new JMenuItem("Test");

    public Menu(Controller controller)
    {
        this.controller = controller;

        newProject.addActionListener(controller);
        file.add(newProject);
        openProject.addActionListener(controller);
        file.add(openProject);
        saveProject.addActionListener(controller);
        file.add(importImage);
        importImage.addActionListener(controller);
        file.add(saveProject);
        closeProject.addActionListener(controller);
        file.add(closeProject);
        exit.addActionListener(controller);
        file.add(exit);
        file.addActionListener(controller);
        add(file);

        undo.addActionListener(controller);
        edit.add(undo);
        redo.addActionListener(controller);
        edit.add(redo);
        add(edit);

        hideInfo.addActionListener(controller);
        view.add(hideInfo);
        add(view);

        apply.addActionListener(controller);
        filter.add(apply);
        reload.addActionListener(controller);
        filter.add(reload);
        add(filter);

        testitem.addActionListener(controller);
        test.add(testitem);
        add(test);
    }
}
