package View.CustomComponents;

import Model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Gabriel on 01/05/2015.
 */
public class ChoosePencilSizeButton extends JButton
{
    private static final long serialVersionUID = 9193692576666062267L;

    private Model model;
    private int brushSize = 12;
    private ChoosePencilSizeButton thisOne = this;
    private boolean isWhite;

    public ChoosePencilSizeButton(Model model, boolean isWhite)
    {
        super("12");
        this.model = model;
        this.isWhite = isWhite;

        setFont(new Font(Font.SANS_SERIF, Font.LAYOUT_LEFT_TO_RIGHT, 10));
        if (!isWhite)
            setForeground(Color.WHITE);

        setMinimumSize(new Dimension(25, 25));
        setMaximumSize(new Dimension(25, 25));
        setPreferredSize(new Dimension(25, 25));
        setSize(new Dimension(25, 25));

        setToolTipText("Chose Brush Size");
        addActionListener(new ChooseColorActionListener());

        setBackground(new Color(80, 80, 80));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private class ChooseColorActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            Integer[] availableSizes = new Integer[152];
            for (int i = 0; i < 152; i++)
                availableSizes[i] = i + 1;
            Object o = JOptionPane.showInputDialog(thisOne, "Choose a brush size", "Brush Size Chooser", JOptionPane.YES_OPTION, null, availableSizes, availableSizes[0]);
            if (o != null)
                brushSize = (int) o;
            setText(String.valueOf(brushSize));
            model.setPencilSize(brushSize);
        }
    }
}
