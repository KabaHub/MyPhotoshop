package View;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonModel;
import javax.swing.JButton;

public class CloseButton extends JButton {

	private static final long serialVersionUID = 9193692576666061167L;
	
	BufferedImage img;
	
	BufferedImage imgHover;
	
	public CloseButton(){
		//super(new ImageIcon("ressources/images/close-icon.png"));
		super();
		setMaximumSize(new Dimension(17, 12));
        setPreferredSize(new Dimension(17, 12));
        setSize(new Dimension(17, 12));
        setToolTipText("Close this Project");
        
        setBackground(new Color(0,0,0,0));
		//setForeground(Color.LIGHT_GRAY);
		setFocusable(false);
		setContentAreaFilled(false);
		setBorderPainted(false);
		setRolloverEnabled(true);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		try{
			img = ImageIO.read(new File("ressources/images/close-icon.png"));
			imgHover = ImageIO.read(new File("ressources/images/close-icon-hover.png"));
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

		ButtonModel model = getModel();
		
		g2d.drawImage(img, 5, 3, this); //TODO exceptions

		if (!model.isEnabled()) {
//			setForeground(Color.LIGHT_GRAY);
		} else {
//			setForeground(Color.BLACK);
			if (model.isRollover()) {
//				setForeground(Color.GRAY);
			} else {
//				setForeground(Color.BLACK);

			}
		}

		if (model.isPressed()) {
//			g2d.setPaint(Color.RED);
//			g2d.fillRect(0, 0, getWidth(), getHeight());
//			setForeground(Color.WHITE);
		} else {
//			g2d.setPaint(Color.BLUE);
//			g2d.fillRect(0, 0, getWidth(), getHeight());
//			setForeground(Color.GREEN);
		}

		if (model.isRollover()) {
//			setForeground(Color.YELLOW);
			g2d.drawImage(imgHover, 5, 3, this);
		}

		g2d.dispose();
		super.paintComponent(g);
	}

}
