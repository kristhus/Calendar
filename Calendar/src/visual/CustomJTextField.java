package visual;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;

public class CustomJTextField extends JTextField implements FocusListener, MouseMotionListener, MouseListener {

	private JTextField textField;
	private Icon searchIcon;
	private String hint;
	private Insets inset;
	private Boolean hasIcon;
	private Boolean isPassword;

	public CustomJTextField(JTextField textField, String iconPath, String hint) {
		this.textField = textField;
		ImageIcon icon = createIcon(iconPath);
		isPassword = false;
		if (hint.toLowerCase().contains("password") || hint.toLowerCase().contains("passord")) {
			isPassword = true;
		}
		if(hasIcon) {
			setIcon(icon);
			MatteBorder mBorder = new MatteBorder(icon);
			CompoundBorder border = new CompoundBorder(textField.getBorder(), mBorder);
			this.inset = border.getBorderInsets(textField);
		}
		this.hint = hint;

		addFocusListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);

	}


	@Override
	protected void paintComponent(Graphics g) {
	    if(!isPassword){
	    	super.paintComponent(g);
	    }


		int hintsX = 2;

		if(searchIcon!=null) {
			int iconWidth = searchIcon.getIconWidth();
			int iconHeight = searchIcon.getIconHeight();
			int x = (inset.left-iconWidth);
			hintsX = x+iconWidth; // To avoid overlappin
			int y = (this.getHeight()-iconHeight)/2;
			searchIcon.paintIcon(this, g, x, y);
		}

		setMargin(new Insets(2,hintsX,2,2));

	    if ( this.getText().equals("")) {
	    	int height = this.getHeight();
	    	Font orgFont = this.getFont();
	    	int h = orgFont.getSize();
	    	int hintsY = (height-h)/2 + h;
	        Font italicFont = new Font(orgFont.getName(), Font.ITALIC, orgFont.getSize());
	        g.setFont(italicFont);
	        g.setColor(UIManager.getColor("textInactiveText"));
	        Graphics2D g2d = (Graphics2D) g;
	        g2d.drawString(hint, this.getInsets().left, hintsY);
	        RenderingHints renderHints = g2d.getRenderingHints();
	        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	        g2d.setRenderingHints(renderHints);

	        g.setFont(orgFont);
	        }
	    else if(isPassword) {
	    	Graphics2D g2d = (Graphics2D) g;
	    	int x = getInsets().left;
	    	int y = (getHeight()-getFont().getSize())/2 + getFont().getSize();
	    	if( searchIcon != null) {
	    		x= inset.left - searchIcon.getIconWidth();
	    		y = (y-searchIcon.getIconHeight())/2;
	    	}
	    	String secure = "";
	    	for (int i = 0; i < getText().length(); i++) {
	    		secure += "*";
	    	}
	    	g2d.drawString(secure, x, y);
	    }
	}

	public ImageIcon createIcon(String iconPath) {
		try {
			hasIcon = true;
			return new ImageIcon(this.getClass().getResource(iconPath));
		} catch(NullPointerException e) {
			System.out.println("Could not find the image with filename " + iconPath);
			hasIcon = false;
			return null;
		}
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub
		this.repaint();

	}

	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
		this.repaint();

	}

	public void setIcon(Icon icon) {
		searchIcon = icon;
	}
	public void setIcon(String path) {
		searchIcon = createIcon(path);
	}
	public void removeIcon() {
		searchIcon = null;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}


	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void mouseMoved(MouseEvent m) {
		// TODO Auto-generated method stub
		if (isFocusOwner()) {
			if(hasIcon && m.getX() <= searchIcon.getIconWidth()+5) {
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			else{
				setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
			}
		}
	}


	@Override
	public void mouseClicked(MouseEvent m) {
		// TODO Auto-generated method stub
		if(hasIcon && m.getX() <= searchIcon.getIconWidth()+5 && isFocusOwner()) {
			// TODO: Do the search or w/e  This may not be needed, depending on if the search is automatic whenever you enter a letter
			System.out.println("Clicked the searchIcon");
		}
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}



}