package View.CustomComponents;

import Model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Gabriel on 01/05/2015.
 */
public class ChoosePencilTypeButton extends JButton
{
    private static final long serialVersionUID = 9193692576666062267L;

    private Model model;
    private String pencilType = "Circle";
    private ChoosePencilTypeButton thisOne = this;

    public ChoosePencilTypeButton(Model model)
    {
        super();
        this.model = model;

        setMaximumSize(new Dimension(16, 16));
        setPreferredSize(new Dimension(16, 16));
        setSize(new Dimension(16, 16));

        addActionListener(new ChooseColorActionListener());

        setBackground(new Color(80, 80, 80));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        switch (pencilType)
        {
            case "Circle":
                g.fillOval(0, 0, 16, 16);
                break;
            case "Square":
                g.fillRect(0, 0, 16, 16);
                break;
        }

        g2d.dispose();
        super.paintComponent(g);
    }

    private class ChooseColorActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String[] availableTypes = {"Circle", "Square"};
            pencilType = (String) JOptionPane.showInputDialog(thisOne, "Choose a brush size", "Brush Size Chooser", JOptionPane.QUESTION_MESSAGE, null, availableTypes, null);
            repaint();
            model.setPencilType(pencilType);
        }
    }
}
