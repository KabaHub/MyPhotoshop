package View.CustomComponents;

import Model.Model;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Gabriel on 01/05/2015.
 */
public class ToolButton extends JButton
{
    private enum ToolType
    {
        MOVE_LAYER_TOOL,
        PENCIL_TOOL,
        ERASER_TOOL,
        EYEDROPPER_TOOL,
        SELECT_SIZE_TOOL,
        SELECT_COLOR_TOOL
    }

    public static final ToolType MOVE_LAYER_TOOL = ToolType.MOVE_LAYER_TOOL;
    public static final ToolType PENCIL_TOOL = ToolType.PENCIL_TOOL;
    public static final ToolType ERASER_TOOL = ToolType.ERASER_TOOL;
    public static final ToolType EYEDROPPER_TOOL = ToolType.EYEDROPPER_TOOL;
    public static final ToolType SELECT_SIZE_TOOL = ToolType.SELECT_SIZE_TOOL;
    public static final ToolType SELECT_COLOR_TOOL = ToolType.SELECT_COLOR_TOOL;

    private static final long serialVersionUID = 9193692576666062267L;

    private BufferedImage img;

    private Model model;
    private ToolType toolType;

    public static ToolButton getNewButton(Model model, ToolType toolType, boolean isLookAndFeelWhite)
    {
        String iconFileName = getIconFileName(toolType, isLookAndFeelWhite);
        return new ToolButton(model, toolType, iconFileName, isLookAndFeelWhite);
    }

    private ToolButton(Model model, ToolType toolType, String iconFileName, boolean isLookAndFeelWhite)
    {
        super(new ImageIcon(iconFileName));
        this.model = model;
        this.toolType = toolType;

        switch (toolType)
        {
            case MOVE_LAYER_TOOL:
                setToolTipText("Move Layer");
                break;
            case PENCIL_TOOL:
                setToolTipText("Pencil Tool");
                break;
            case ERASER_TOOL:
                setToolTipText("Eraser Tool");
                break;
            case EYEDROPPER_TOOL:
                setToolTipText("EyeDropper Tool");
                break;
            case SELECT_SIZE_TOOL:
                setToolTipText("Select Size of Pencil");
                break;
            case SELECT_COLOR_TOOL:
                setToolTipText("Select Color");
                break;
        }

        addActionListener(new ToolButtonActionListener());

        setBackground(new Color(80, 80, 80));
//        setFocusable(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        try
        {
            img = ImageIO.read(new File(iconFileName));
        } catch (IOException e)
        {
            System.err.println(e.getMessage());
        }

    }

    private static String getIconFileName(ToolType toolType, boolean isLookAndFeelWhite)
    {
        String iconFileName = null;
        switch (toolType)
        {
            case MOVE_LAYER_TOOL:
            {
                if (isLookAndFeelWhite)
                    iconFileName = "asset/move.png";
                else
                    iconFileName = "asset/move-white.png";
                break;
            }
            case PENCIL_TOOL:
            {
                if (isLookAndFeelWhite)
                    iconFileName = "asset/pencil-icon.png";
                else
                    iconFileName = "asset/pencil-icon-white.png";
                break;
            }
            case ERASER_TOOL:
            {
                if (isLookAndFeelWhite)
                    iconFileName = "asset/eraser-icon.png";
                else
                    iconFileName = "asset/eraser-icon-white.png";
                break;
            }
            case EYEDROPPER_TOOL:
            {
                if (isLookAndFeelWhite)
                    iconFileName = "asset/eyedropper.png";
                else
                    iconFileName = "asset/eyedropper-white.png";
                break;
            }
            case SELECT_SIZE_TOOL:
                break;
            case SELECT_COLOR_TOOL:
                break;
        }
        return iconFileName;
    }

    private class ToolButtonActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            switch (toolType)
            {
                case MOVE_LAYER_TOOL:
                {
                    break;
                }
                case PENCIL_TOOL:
                {
                    break;
                }
                case ERASER_TOOL:
                {
                    break;
                }
                case EYEDROPPER_TOOL:
                {
                    break;
                }
                case SELECT_SIZE_TOOL:
                {
                    break;
                }
                case SELECT_COLOR_TOOL:
                {
                    break;
                }
            }
        }
    }
}
