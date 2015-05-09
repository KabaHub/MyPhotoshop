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
    private int brushSize = 8;
    private ChoosePencilSizeButton thisOne = this;

    public ChoosePencilSizeButton(Model model)
    {
        super("12");
        this.model = model;

        addActionListener(new ChooseColorActionListener());

        setBackground(new Color(80, 80, 80));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private class ChooseColorActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            Integer[] availableSizes = new Integer[153];
            for (int i = 0; i < 153; i++)
                availableSizes[i] = i;
            brushSize = (int) JOptionPane.showInputDialog(thisOne, "Choose a brush size", "Brush Size Chooser", JOptionPane.QUESTION_MESSAGE, null, availableSizes, null);
            setText(String.valueOf(brushSize));
            model.setPencilSize(brushSize);
        }
    }
}
