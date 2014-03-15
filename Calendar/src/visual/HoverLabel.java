package visual;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;

public class HoverLabel extends JLabel implements MouseListener {

	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	
	private boolean hover;
	private Color background;
	private Color hoverFontColor;
	private Color oldBackground;
	private Icon icon;
	private JLabel label;
	private Insets insets;
	private Boolean selected = false;
	private String actionCommand = "";
	
	public HoverLabel(JLabel label, Color background, Color hoverFontColor, Color defaultBackgroundColor, String iconPath) {
		setText(label.getText());
		setOpaque(true);
		hover = false;
		this.label = label;
		this.background = background;
		this.hoverFontColor = hoverFontColor;
		this.oldBackground = defaultBackgroundColor;
		actionCommand = "clicked";
		
		if(iconPath != "") {
			ImageIcon icon = createIcon(iconPath);
			this.icon = icon;
			MatteBorder mBorder = new MatteBorder(icon);
			CompoundBorder border = new CompoundBorder(label.getBorder(), mBorder);
			this.insets = border.getBorderInsets(label);
		}
		addMouseListener(this);
	}
	
	public void createBackGround() {
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(icon!=null && selected) {
			int iconWidth = icon.getIconWidth();
			int iconHeight = icon.getIconHeight();
			int x = (this.getWidth()-insets.right);
			int y = (this.getHeight()-iconHeight)/2;
			icon.paintIcon(this, g, x, y);
		}
		if(hover) {
			setBackground(background);
		}
		else {
			setBackground(oldBackground);
		}
	}
	
	
	public ImageIcon createIcon(String iconPath) {
		try {
			return new ImageIcon(this.getClass().getResource(iconPath));
		} catch(NullPointerException e) {
			System.out.println("Could not find the image with filename " + iconPath);
			e.printStackTrace();
			return null;
		}
	}
	
	public void setIcon(Icon icon) {
		this.icon = icon;
	}
	public void setIcon(String path) {
		icon = createIcon(path);
		this.repaint();
	}	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Boolean old = selected;
		selected = !selected;
		firePropertyChange(actionCommand, old, selected);
		this.repaint();
	}
	public void testPaint() {
		this.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		hover = true;
		setBackground(background);
		setForeground(hoverFontColor);
		this.repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		hover = false;
		setBackground(oldBackground);
		setForeground(Color.black);
		this.repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		super.addPropertyChangeListener(listener);
	}
	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}
	
	public void setActionCommand(String actionCommand) {
		this.actionCommand = actionCommand;
	}
	
	
	public void setSelected(Boolean isSelected) {
		selected = isSelected;
	}
	
	public Boolean isSelected() {
		return selected;
	}

}
