package visual;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SpringLayout;

public class CalendarView extends JPanel {
	
	private int mouseX;
	private int mouseY;
	
	private final PanelListener panelListener = new PanelListener();
	
	private int dayWidth;
	private int dayHeight;
	
	private final Color dayPanelColor = new Color(255, 219, 158);
	private final Color dayPanelColorAlt = new Color (255, 236, 204);
	
	private Color orgColor;
	
	private Container header;
	
	private SpringLayout calenderLayout;
	
	private final Day[] days = {Day.Mandag, Day.Tirsdag, Day.Onsdag, Day.Torsdag, Day.Fredag, Day.L�rdag, Day.S�ndag};
	
	public CalendarView() {

		
		dayWidth = 800;
		dayHeight = 900;
		
		setBackground(Color.white);
		calenderLayout = new SpringLayout();
		setLayout(calenderLayout);
		
		createHeader();
		createWeek();
		
	}

	private void createHeader() {
		header = new Container();
		header.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0; c.ipadx = 0;
		
		String iconDefault = "/buttons/left-up.png";
		String iconHover = "/buttons/left-hover.png";
		String iconPressed = "/buttons/left-down.png";
		header.add(new CustomButton(iconDefault, iconPressed, iconHover), c);
		c.ipadx = 10;
		for(int d = 0; d < 7; d++) {
			c.gridx++;
			JPanel headerDayPanel = new JPanel();
			headerDayPanel.setPreferredSize(new Dimension(dayWidth/7-10, dayHeight/24));
			headerDayPanel.setBackground(new Color(255,208,112));
			headerDayPanel.setBorder(BorderFactory.createLineBorder(Color.black));
			JLabel dayDescription = new JLabel("<html>" + days[d].toString() + "<br>" + "'DATO'</html>", JLabel.CENTER);
			headerDayPanel.add(dayDescription);
			header.add(headerDayPanel, c);
		}
		c.ipadx = 0;
		c.gridx++;
		iconDefault = "/buttons/right-up.png";
		iconHover = "/buttons/right-hover.png";
		iconPressed = "/buttons/right-down.png";
		header.add(new CustomButton(iconDefault, iconPressed, iconHover), c);
		calenderLayout.putConstraint(SpringLayout.NORTH, header, 50, SpringLayout.NORTH, this);
		calenderLayout.putConstraint(SpringLayout.WEST, header, 0, SpringLayout.WEST, this);
		add(header);
		
		JLabel backWeek = new JLabel("LAST WEEK"); //TODO get the date one week of current calendar
		Font weekFont = new Font(Font.SERIF,Font.BOLD , 20 );
		backWeek.setFont(weekFont);
		calenderLayout.putConstraint(SpringLayout.NORTH, backWeek, 20, SpringLayout.NORTH, this);
		calenderLayout.putConstraint(SpringLayout.WEST, backWeek, 10, SpringLayout.WEST, this);
		add(backWeek);
		
		JLabel forwardWeek = new JLabel("NEXT WEEK"); //TODO get the date one week of current calendar
		forwardWeek.setFont(weekFont);
		calenderLayout.putConstraint(SpringLayout.NORTH, forwardWeek, 20, SpringLayout.NORTH, this);
		calenderLayout.putConstraint(SpringLayout.EAST, forwardWeek, -10, SpringLayout.EAST, this);
		add(forwardWeek);
		
		
	}

	private void createWeek() {
		
		JScrollPane jsp = new JScrollPane(this);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		Container dayContainer = new Container();
		dayContainer.setLayout(new GridBagLayout());
		
		dayContainer.add(createTimeSlots());
		for(int d = 0; d < 7; d++) {
			JPanel dayPanel = createDay();
			dayContainer.add(dayPanel);
		}
		
		
		calenderLayout.putConstraint(SpringLayout.NORTH, dayContainer, 0, SpringLayout.SOUTH, header);
		calenderLayout.putConstraint(SpringLayout.WEST, dayContainer, 0 , SpringLayout.WEST, this);
		add(dayContainer);
	}
	
	private JPanel createTimeSlots() {
		int width = 35;
		int height = dayHeight/24;
		
		JPanel dayPanel = new JPanel();
		dayPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0; c.fill = GridBagConstraints.VERTICAL;
		for (int h = 0; h < 24; h++) {
			JPanel hour = new JPanel();
			hour.setBorder(BorderFactory.createLineBorder(Color.gray));
			hour.setPreferredSize(new Dimension(width, height));
			if(h%2 == 0) {
				hour.setBackground(Color.white);
			}
			else {
				hour.setBackground(dayPanelColor);
			}
			String timeStamp = "";
			if(h < 9) {
				timeStamp = "0"+h+":00";
			}
			else {
				timeStamp = h+":00";
			}
			JLabel timeLabel = new JLabel(timeStamp);
			hour.add(timeLabel);
			dayPanel.add(hour, c);
			c.gridy++;
			
		}
		return dayPanel;
		
	}
	
	private JPanel createDay() {
		// TODO Auto-generated method stub
		JPanel dayPanel = new JPanel();
		dayPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0; c.fill = GridBagConstraints.VERTICAL;
		for (int h = 0; h < 24; h++) {
			JPanel hour = new JPanel();
			hour.setBorder(BorderFactory.createLineBorder(Color.gray));
			hour.setPreferredSize(new Dimension(dayWidth/7, dayHeight/24));
			if(h%2 == 0) {
				hour.setBackground(Color.white);
			}
			else {
				hour.setBackground(new Color(255, 219, 158));
			}
			hour.addMouseListener(panelListener);
			hour.addMouseMotionListener(panelListener);
			dayPanel.add(hour, c);
			c.gridy++;
			
		}
		return dayPanel;
	}
	
	private enum Day {
		Mandag, Tirsdag, Onsdag, Torsdag, Fredag, L�rdag, S�ndag;
	}

	
	public class PanelListener implements MouseListener, MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent arg0) {
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			System.out.println(arg0.getX() + " , " + arg0.getY());
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			((JPanel) arg0.getSource()).setBorder(BorderFactory.createRaisedBevelBorder()); //TODO DISCUSS: REMOVE?
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			((JPanel) arg0.getSource()).setBorder(BorderFactory.createLineBorder(Color.gray));
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			orgColor = ((JPanel) arg0.getSource()).getBackground();
			
			( (JPanel) arg0.getSource()).setBackground(dayPanelColorAlt);
			((JPanel) arg0.getSource()).setBorder(BorderFactory.createLoweredBevelBorder());
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			( (JPanel) arg0.getSource()).setBackground(orgColor);
			((JPanel) arg0.getSource()).setBorder(BorderFactory.createLineBorder(Color.gray));
			
			//TODO CREATE NY AVTALE
		}
		
	}
	
}