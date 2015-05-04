package Control;

import View.ChooseFilterWindow;
import View.ImageFileFilter;
import View.MainWindow;
import Model.Model;
import Model.Project;
import Model.ImageState;

import javax.imageio.ImageIO;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.*;

/**
 * Created by duplai_g on 4/22/15.
 */
public class Controller implements ActionListener
{
    private Model model;
    private MainWindow mainWindow;
    private JFileChooser projectFileChooser = new JFileChooser(System.getProperty("user.dir"));
    private JFileChooser imageFileChooser = new JFileChooser(System.getProperty("user.dir"));
    private JFileChooser saveFileChooser = new JFileChooser(System.getProperty("user.dir"));
    private JFileChooser exportFileChooser = new JFileChooser(System.getProperty("user.dir"));

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
        } else if (e.getActionCommand().contentEquals("New Project"))
        {
            model.addProject();
        } else if (e.getActionCommand().contentEquals("Open Project"))
        {
            ImageFileFilter mypsdFileFilter = new ImageFileFilter("MyPhotoshop (*.myPSD)", ".myPSD");
            ImageFileFilter pngFileFilter = new ImageFileFilter("PNG (*.png)", ".png");
            ImageFileFilter jpgFileFilter = new ImageFileFilter("JPEG (*.jpg;*.jpeg)", new String[]{".jpg", ".jpeg"});
            ImageFileFilter gifFileFilter = new ImageFileFilter("GIF (*.gif)", ".gif");
            ImageFileFilter bmpFileFilter = new ImageFileFilter("BMP (*.bmp)", ".bmp");
            projectFileChooser.addChoosableFileFilter(mypsdFileFilter);
            projectFileChooser.addChoosableFileFilter(pngFileFilter);
            projectFileChooser.addChoosableFileFilter(jpgFileFilter);
            projectFileChooser.addChoosableFileFilter(gifFileFilter);
            projectFileChooser.addChoosableFileFilter(bmpFileFilter);
            if (projectFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                File f = projectFileChooser.getSelectedFile();
                model.addProject(f);
            }
        } else if (e.getActionCommand().contentEquals("Import Image"))
        {
            ImageFileFilter pngFileFilter = new ImageFileFilter("PNG (*.png)", ".png");
            ImageFileFilter jpgFileFilter = new ImageFileFilter("JPEG (*.jpg;*.jpeg)", new String[]{".jpg", ".jpeg"});
            ImageFileFilter gifFileFilter = new ImageFileFilter("GIF (*.gif)", ".gif");
            ImageFileFilter bmpFileFilter = new ImageFileFilter("BMP (*.bmp)", ".bmp");
            imageFileChooser.addChoosableFileFilter(pngFileFilter);
            imageFileChooser.addChoosableFileFilter(jpgFileFilter);
            imageFileChooser.addChoosableFileFilter(gifFileFilter);
            imageFileChooser.addChoosableFileFilter(bmpFileFilter);
            if (imageFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                File f = imageFileChooser.getSelectedFile();
                mainWindow.getCurrentTab().getProject().importImage(f);
            }
        } else if (e.getActionCommand().contentEquals("Export Image"))
        {
            Project p = mainWindow.getCurrentTab().getProject();
            ImageFileFilter pngFileFilter = new ImageFileFilter("PNG (*.png)", ".png", "PNG");
            ImageFileFilter jpgFileFilter = new ImageFileFilter("JPEG (*.jpg,*.jpeg)", new String[]{".jpg", ".jpeg"}, "JPG");
            ImageFileFilter gifFileFilter = new ImageFileFilter("GIF (*.gif)", ".gif", "GIF");
            ImageFileFilter bmpFileFilter = new ImageFileFilter("BMP (*.bmp)", ".bmp", "BMP");
            exportFileChooser.setAcceptAllFileFilterUsed(false);
            exportFileChooser.addChoosableFileFilter(pngFileFilter);
            exportFileChooser.addChoosableFileFilter(jpgFileFilter);
            exportFileChooser.addChoosableFileFilter(gifFileFilter);
            exportFileChooser.addChoosableFileFilter(bmpFileFilter);
            boolean satisfiedOfName = false;
            while (!satisfiedOfName)
            {
                satisfiedOfName = true;
                if (exportFileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
                {
                    File f = exportFileChooser.getSelectedFile();
                    ImageFileFilter imageFileFilter = ((ImageFileFilter) (exportFileChooser.getFileFilter()));
                    if (!f.getName().endsWith(imageFileFilter.getExtension()))
                        f = new File(f.getParentFile(), f.getName() + imageFileFilter.getExtension());
                    boolean ready = true;
                    if (f.exists())
                    {
                        int selectedOption = JOptionPane.showConfirmDialog(null,
                                "File " + f.getName() + " already exists." + System.getProperty("line.separator")
                                        + "Do you want to overwrite it ?",
                                "Test",
                                JOptionPane.YES_NO_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE);
                        if (selectedOption == JOptionPane.NO_OPTION)
                        {
                            ready = false;
                            satisfiedOfName = false;
                        }
                        if (selectedOption == JOptionPane.CANCEL_OPTION)
                            ready = false;
                    }
                    if (ready)
                    {
                        System.out.println("Going to Save " + f.getName());
                        BufferedImage image = p.getImagePanel().getImage();
                        try
                        {
                            ImageIO.write(image, imageFileFilter.getFormat(), f);
                        } catch (IOException e1)
                        {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        } else if (e.getActionCommand().contentEquals("Save Project"))
        {
            ImageFileFilter saveFileFilter = new ImageFileFilter("MyPhotoshop (*.myPSD)", ".myPSD");
            saveFileChooser.addChoosableFileFilter(saveFileFilter);
            boolean satisfiedOfName = false;
            while (!satisfiedOfName)
            {
                satisfiedOfName = true;
                if (saveFileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
                {
                    Project p = mainWindow.getCurrentTab().getProject();
                    File f = saveFileChooser.getSelectedFile();
                    if (!f.getName().endsWith(".myPSD"))
                        f = new File(f.getParentFile(), f.getName() + ".myPSD");
                    boolean ready = true;
                    if (f.exists())
                    {
                        int selectedOption = JOptionPane.showConfirmDialog(null,
                                "File " + f.getName() + " already exists." + System.getProperty("line.separator")
                                        + "Do you want to overwrite it ?",
                                "Test",
                                JOptionPane.YES_NO_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE);
                        if (selectedOption == JOptionPane.NO_OPTION)
                        {
                            ready = false;
                            satisfiedOfName = false;
                        }
                        if (selectedOption == JOptionPane.CANCEL_OPTION)
                            ready = false;
                    }
                    if (ready)
                    {
                        try
                        {
                            p.prepareToSerialization();
                            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
                            oos.writeObject(p);
                        } catch (IOException e1)
                        {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        } else if (e.getActionCommand().contentEquals("Close Project"))
        {
            mainWindow.removeFromTabbedPanel(mainWindow.getCurrentTab());
        } else if (e.getActionCommand().contentEquals("Print"))
        {
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            if (printerJob.printDialog())
            {
                try {
                    printerJob.setJobName(mainWindow.getCurrentTab().getProjectName());
                    double x = mainWindow.getCurrentTab().getProject().getImagePanel().getWidth();
                    double y = mainWindow.getCurrentTab().getProject().getImagePanel().getHeight();
                    PageFormat pageFormat = printerJob.defaultPage();
                    Paper paper = new Paper();
                    paper.setSize(2*x, 2*y);
                    paper.setImageableArea(0, 0, 2*x, 2*y);
                    pageFormat.setPaper(paper);
                    printerJob.setPrintable(mainWindow.getCurrentTab().getProject().getImagePanel(), pageFormat);
                    printerJob.print();
                } catch (PrinterException e1)
                {
                    e1.printStackTrace();
                }
            }
            System.out.println("test");
        } else if (e.getActionCommand().contentEquals("Exit"))
        {
            System.exit(0);
        } else if (e.getActionCommand().contentEquals("Undo"))
        {
            mainWindow.getCurrentTab().getProject().undo();
        } else if (e.getActionCommand().contentEquals("Redo"))
        {
            mainWindow.getCurrentTab().getProject().redo();
        } else if (e.getActionCommand().contentEquals("Show Project Toolbar"))
        {
            if (mainWindow.getCurrentTab().getInfoPanel().isVisible())
                mainWindow.getCurrentTab().setInfoPanelVisibility(false);
            else
                mainWindow.getCurrentTab().setInfoPanelVisibility(true);
        } else if (e.getActionCommand().contentEquals("Apply Filter"))
        {
            if (!model.getFilters().isEmpty())
                new ChooseFilterWindow(model.getFilters(), mainWindow.getCurrentTab().getProject());
            else
            {
                JOptionPane.showMessageDialog(null, "No Plugin found in plugin/ !", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getActionCommand().contentEquals("Reload Filters"))
        {
            model.reloadPlugins();
            JOptionPane.showMessageDialog(null, "Plugin Reloaded from plugin/", "Information", JOptionPane.WARNING_MESSAGE);
        } else if (e.getActionCommand().contentEquals("Test"))
        {
            System.out.println("History :");
            System.out.println("--------------------------");
            for (ImageState i : mainWindow.getCurrentTab().getProject().getHistory())
                System.out.println(i.getAppliedIPlugin());
        } else if (e.getActionCommand().contentEquals("CloseTab"))
        {
            mainWindow.removeFromTabbedPanel(mainWindow.getCurrentTab());
        } else if (e.getActionCommand().contentEquals("About"))
        {
            JOptionPane.showMessageDialog(null, "MyPhotoshop by Gabriel DUPLAIX from EPITA. g.duplaix.pro@gmail.com", "About", JOptionPane.PLAIN_MESSAGE);
        } else
            System.out.println(e.getActionCommand() + " not yet Implemented");
    }
}
