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
    private boolean isWhite;

    public ChoosePencilTypeButton(Model model, boolean isWhite)
    {
        super();
        this.model = model;
        this.isWhite = isWhite;

        setMaximumSize(new Dimension(20, 20));
        setPreferredSize(new Dimension(20, 20));
        setSize(new Dimension(20, 20));
        setToolTipText("Choose Pencil Shape");

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

        if (!isWhite)
            g.setColor(Color.WHITE);

        if (pencilType != null)
        {
            switch (pencilType)
            {
                case "Circle":
                    g.fillOval(0, 0, 16, 16);
                    break;
                case "Square":
                    g.fillRect(0, 0, 16, 16);
                    break;
            }
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
            if (pencilType != null)
                model.setPencilType(pencilType);
            repaint();
        }
    }
}
