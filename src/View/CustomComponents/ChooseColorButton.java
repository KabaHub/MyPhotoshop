package View.CustomComponents;

import Model.Model;
import Model.ToolType;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Gabriel on 01/05/2015.
 */
public class ChooseColorButton extends JButton
{
    private static final long serialVersionUID = 9193692576666062267L;

    private Model model;
    private Color color = Color.BLACK;

    public ChooseColorButton(Model model)
    {
        super();
        this.model = model;

        setMaximumSize(new Dimension(24, 24));
        setPreferredSize(new Dimension(24, 24));
        setSize(new Dimension(24, 24));
        setSize(24, 24);
        setToolTipText("Choose Color");

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

        g2d.setColor(color);
        g2d.fillRect(0, 0, 24, 24);

        g2d.dispose();
        super.paintComponent(g);
    }

    private class ChooseColorActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            color = JColorChooser.showDialog(null, "Choose Color", Color.BLACK);
            color = color != null ? color : Color.BLACK;
            repaint();
            model.setChosenColor(color);
        }
    }
}
