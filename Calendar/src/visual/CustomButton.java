package visual;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CustomButton extends JPanel implements MouseMotionListener, MouseListener {
	
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	private ImageIcon iconDefault;
	private ImageIcon iconPressed;
	private ImageIcon iconHover;
	
	private JLabel iconLab;

	private Boolean outside = true;
	
	private Image currentIcon;
	
	private String actionCommand = "DEFAULT";
	
	public CustomButton(String iconDefault, String iconPressed, String iconHover) {
		this.iconDefault = createIcon(iconDefault);
		this.iconHover = createIcon(iconHover);
		this.iconPressed = createIcon(iconPressed);
		
		
		setBackground(Color.white);
		currentIcon = this.iconDefault.getImage();
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(currentIcon.getWidth(this), currentIcon.getHeight(this)));
	}
	
	public ImageIcon createIcon(String iconPath) {
		try {
			return new ImageIcon(this.getClass().getResource(iconPath));
		} catch(NullPointerException e) {
			System.out.println("Could not find the image with filename " + iconPath);
			return null;
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g); 
		g.drawImage(currentIcon, 0, 0, this);
	}
	/*
	 img - the specified image to be drawn. This method does nothing if img is null.
	dx1 - the x coordinate of the first corner of the destination rectangle.
	dy1 - the y coordinate of the first corner of the destination rectangle.
	dx2 - the x coordinate of the second corner of the destination rectangle.
	dy2 - the y coordinate of the second corner of the destination rectangle.
	sx1 - the x coordinate of the first corner of the source rectangle.
	sy1 - the y coordinate of the first corner of the source rectangle.
	sx2 - the x coordinate of the second corner of the source rectangle.
	sy2 - the y coordinate of the second corner of the source rectangle.
	observer -
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		// NOTIFY ON CLICK
		firePropertyChange(actionCommand, null, null); // If this was meant to have buttonestates, it would need changes, but this is only to notify another class that the buttons where pressed
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		outside = false;
		currentIcon = iconHover.getImage();
		repaint();
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		outside = true;
		currentIcon = iconDefault.getImage();
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		currentIcon = iconPressed.getImage();
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(outside) {
			currentIcon = iconDefault.getImage();
		}
		else {
			currentIcon = iconHover.getImage();
		}
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setActionCommand(String actionCommand) {
		this.actionCommand = actionCommand;
	}
	
	public Image getImage() {
		return this.currentIcon;
	}
	
	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		super.addPropertyChangeListener(listener);
	}
	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}
	
	
}
