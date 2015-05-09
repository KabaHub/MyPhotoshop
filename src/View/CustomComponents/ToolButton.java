package View.CustomComponents;

import javax.imageio.ImageIO;
import javax.swing.*;
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
	private String iconFileName;
	private ToolType toolType;

	public ToolButton(ToolType toolType){
		super();
		this.toolType = toolType;
		setMaximumSize(new Dimension(20, 20));
        setPreferredSize(new Dimension(20, 20));
        setSize(new Dimension(20, 20));

		switch (toolType)
		{
			case MOVE_LAYER_TOOL:
			{
				setToolTipText("Move Layer");
				iconFileName = "asset/move.png";
			}
			case PENCIL_TOOL:
			{
				setToolTipText("Pencil Tool");
			}
			case ERASER_TOOL:
			{
				setToolTipText("Eraser Tool");
			}
			case EYEDROPPER_TOOL:
			{
				setToolTipText("EyeDropper Tool");
			}
			case SELECT_SIZE_TOOL:
			{
				setToolTipText("Select Size of Pencil");
			}
			case SELECT_COLOR_TOOL:
			{
				setToolTipText("Select Color");
			}
		}
		addActionListener(new ToolButtonActionListener());

        setBackground(new Color(0,0,0,0));
		setFocusable(false);
//		setContentAreaFilled(false);
//		setBorderPainted(false);
		setRolloverEnabled(true);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		try{
			img = ImageIO.read(new File(iconFileName));
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
        
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		if (toolType == MOVE_LAYER_TOOL)
			g2d.drawImage(img, 2, 4, this);
		else
			g2d.drawImage(img, 0, 0, this);
		g2d.dispose();
		super.paintComponent(g);
	}

	private class ToolButtonActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{

		}
	}
}
