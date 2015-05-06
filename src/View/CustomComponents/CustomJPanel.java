package View.CustomComponents;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Gabriel on 03/05/2015.
 */
public class CustomJPanel extends JPanel {
    private enum CustomTypes
    {
        GREY,
        BLACK
    }
    public static CustomTypes GREY = CustomTypes.GREY;
    public static CustomTypes BLACK = CustomTypes.BLACK;

    public CustomJPanel(CustomTypes customType)
    {
        super();
        init(customType);
    }

    private void init(CustomTypes customTypes)
    {
        setForeground(Color.WHITE);
        switch (customTypes)
        {
            case GREY:
            {
                setBackground(new Color(80, 80, 80));
                break;
            }
            case BLACK:
            {
                setBackground(new Color(60, 60, 60));
                break;
            }
            default:

        }
    }
}
