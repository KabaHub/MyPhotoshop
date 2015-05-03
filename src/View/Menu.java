package View;

import Control.Controller;

import javax.swing.*;
import java.awt.event.KeyEvent;

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
    private JMenuItem importImage = new JMenuItem("Import Image");
    private JMenuItem exportImage = new JMenuItem("Export Image");
    private JMenuItem closeProject = new JMenuItem("Close Project");
    private JMenuItem exit = new JMenuItem("Exit");

    private JMenu edit = new JMenu("Edit");
    private JMenuItem undo = new JMenuItem("Undo");
    private JMenuItem redo = new JMenuItem("Redo");

    private JMenu view = new JMenu("View");
    private JMenuItem hideInfo = new JMenuItem("Show Project Toolbar");

    private JMenu filter = new JMenu("Filter");
    private JMenuItem apply = new JMenuItem("Apply Filter");
    private JMenuItem reload = new JMenuItem("Reload Filters");

    private JMenu help = new JMenu("Help");
    private JMenuItem about = new JMenuItem("About");

    private JMenu test = new JMenu("Test");
    private JMenuItem testitem = new JMenuItem("Test");

    public Menu(Controller controller)
    {
        this.controller = controller;

        newProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
        newProject.addActionListener(controller);
        file.add(newProject);
        openProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        openProject.addActionListener(controller);
        file.add(openProject);
        saveProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        saveProject.addActionListener(controller);
        file.add(saveProject);
        importImage.addActionListener(controller);
        file.add(importImage);
        exportImage.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK));
        exportImage.addActionListener(controller);
        file.add(exportImage);
        closeProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK));
        closeProject.addActionListener(controller);
        file.add(closeProject);
        exit.addActionListener(controller);
        file.add(exit);
        file.addActionListener(controller);
        add(file);

        undo.addActionListener(controller);
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK));
        edit.add(undo);
        redo.addActionListener(controller);
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK));
        edit.add(redo);
        add(edit);

        hideInfo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.CTRL_DOWN_MASK));
        hideInfo.addActionListener(controller);
        view.add(hideInfo);
        add(view);

        apply.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK));
        apply.addActionListener(controller);
        filter.add(apply);
        reload.addActionListener(controller);
        filter.add(reload);
        add(filter);

        about.addActionListener(controller);
        help.add(about);
        add(help);

//        testitem.addActionListener(controller);
//        test.add(testitem);
//        add(test);
    }
}
