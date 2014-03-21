package visual;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneLayout;
import javax.swing.SpringLayout;
import javax.swing.WindowConstants;

import objects.Appointment;
import objects.MeetingRoom;
import objects.Person;
import calculations.DateCalculations;
import calculations.NorCalendar;

public class CalendarView extends JLayeredPane {
	
	private int mouseX;
	private int mouseY;
	
	private final PanelListener panelListener = new PanelListener();
	
	private int dayWidth;
	private int dayHeight;
	
	private final Color dayPanelColor = new Color(74,176,207);
	private final Color dayPanelColorAlt = new Color (177,223,230);
	
	private Color orgColor;
	
	private Container header;
	private ArrayList<TransparentPanel> appointmentPanels;
	private SpringLayout dayLayout;
	
	private SpringLayout calendarLayout;
	
	public static final Day[] days = {Day.Mandag, Day.Tirsdag, Day.Onsdag, Day.Torsdag, Day.Fredag, Day.Lørdag, Day.Søndag};
	
	private NorCalendar cal = new NorCalendar();
	
	private JLabel currentWeek;
	private JLabel[] weekDays = {null, null, null, null, null, null, null};
	private Container dayContainer;
	
	private static ArrayList userCal;
	
	public JLabel[] getWeekDays() {
		return weekDays;
	}

	public CalendarView() {

		
		dayWidth = 800;
		dayHeight = 700;
		
		setBackground(Color.white);
		calendarLayout = new SpringLayout();
		setLayout(calendarLayout);
		
		for (int i = 0; i < 7; i++) {
			weekDays[i] = new JLabel("", JLabel.CENTER);
		}
		
		createHeader();
		createWeek();
		
	}

	private void createHeader() {
		String[] weekDates = cal.getWeekDates();
		header = new Container();
		header.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0; c.ipadx = 0;
		
		String iconDefault = "/buttons/left-up.png";
		String iconHover = "/buttons/left-hover.png";
		String iconPressed = "/buttons/left-down.png";
		
		CustomButton back = new CustomButton(iconDefault, iconPressed, iconHover);
		back.addPropertyChangeListener(new PanelListener());
		back.setActionCommand("BACK");
		header.add(back, c);
		c.ipadx = 10;
		System.out.println();
		for(int d = 0; d < 7; d++) {
			c.gridx++;
			JPanel headerDayPanel = new JPanel();
			headerDayPanel.setPreferredSize(new Dimension(dayWidth/7-10, 1000/24));
			headerDayPanel.setBackground(new Color(255,208,112));
			headerDayPanel.setBorder(BorderFactory.createLineBorder(Color.black));
//			System.out.println(weekDates[d]);
			weekDays[d].setText("<html>" + days[d].toString() + "<br>" + weekDates[d].toString() + "</html>"); // TODO GET THIS DATE
			headerDayPanel.add(weekDays[d]);
			header.add(headerDayPanel, c);
		}
		c.ipadx = 0;
		c.gridx++;
		iconDefault = "/buttons/right-up.png";
		iconHover = "/buttons/right-hover.png";
		iconPressed = "/buttons/right-down.png";
		CustomButton forward = new CustomButton(iconDefault, iconPressed, iconHover);
		forward.addPropertyChangeListener(new PanelListener());
		forward.setActionCommand("FORWARD");
		header.add(forward, c);
		calendarLayout.putConstraint(SpringLayout.NORTH, header, 50, SpringLayout.NORTH, this);
		calendarLayout.putConstraint(SpringLayout.WEST, header, 3, SpringLayout.WEST, this);
		add(header);
		
		JLabel backWeek = new JLabel("LAST WEEK"); //TODO get the date one week of current calendar
		Font weekFont = new Font(Font.SERIF,Font.BOLD , 20 );
		backWeek.setFont(weekFont);
		calendarLayout.putConstraint(SpringLayout.NORTH, backWeek, 20, SpringLayout.NORTH, this);
		calendarLayout.putConstraint(SpringLayout.WEST, backWeek, 10, SpringLayout.WEST, this);
		add(backWeek);
		
		
		JLabel forwardWeek = new JLabel("NEXT WEEK"); //TODO get the date one week of current calendar
		forwardWeek.setFont(weekFont);
		calendarLayout.putConstraint(SpringLayout.NORTH, forwardWeek, 20, SpringLayout.NORTH, this);
		calendarLayout.putConstraint(SpringLayout.EAST, forwardWeek, -10, SpringLayout.EAST, header);
		add(forwardWeek);
		
		
		currentWeek = new JLabel("Uke " + Integer.toString(cal.get(Calendar.WEEK_OF_YEAR)));
//		curWeek.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		currentWeek.setFont(weekFont);
		calendarLayout.putConstraint(SpringLayout.NORTH, currentWeek, 20, SpringLayout.NORTH, this);
		calendarLayout.putConstraint(SpringLayout.WEST, currentWeek, 400, SpringLayout.WEST, this);
		add(currentWeek);
	}

	
	private void createWeek() {
		
		appointmentPanels = new ArrayList();
		
		dayLayout = new SpringLayout();
		
		
		dayContainer = new Container();
		dayContainer.setLayout(new GridBagLayout());
		
		dayContainer.add(createTimeSlots());
		for(int d = 0; d < 7; d++) {
			JPanel dayPanel = createDay();
//			if (d == 0) {
//				dayLayout.putConstraint(SpringLayout.WEST, dayPanel, 35, SpringLayout.WEST, dayContainer.getComponent(dayContainer.getComponentCount()-1));
//			}
//			else {
//				dayLayout.putConstraint(SpringLayout.WEST, dayPanel, 114, SpringLayout.WEST, dayContainer.getComponent(dayContainer.getComponentCount()-1));
//			}
			System.out.println(dayContainer.getComponent(dayContainer.getComponentCount()-1));
			dayContainer.add(dayPanel);
		}
//		daySuperPanel.add(dayContainer);
//		JScrollPane jsp = new JScrollPane(dayContainer);
//		
//		jsp.setPreferredSize(new Dimension(854, 600));
//		jsp.setBackground(Color.white);
//		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//		jsp.setLayout(new ScrollPaneLayout());
//		
		calendarLayout.putConstraint(SpringLayout.NORTH, dayContainer, 0, SpringLayout.SOUTH, header);
		calendarLayout.putConstraint(SpringLayout.WEST, dayContainer, 3 , SpringLayout.WEST, this);
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
				hour.setBackground(new Color(52, 190, 218));
			}
			hour.addMouseListener(panelListener);
			hour.addMouseMotionListener(panelListener);
			dayPanel.add(hour, c);
			c.gridy++;
			
		}
		return dayPanel;
	}
	
	public enum Day {
		Mandag, Tirsdag, Onsdag, Torsdag, Fredag, Lørdag, Søndag;
	}

	
	public class PanelListener implements MouseListener, MouseMotionListener, PropertyChangeListener {

		@Override
		public void mouseDragged(MouseEvent arg0) {
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			for (int i = 0; i < dayContainer.getComponentCount(); i++) {
				for(int j = 0; j < ((Container) dayContainer.getComponent(i)).getComponentCount(); j++) {
					if ((((JPanel) dayContainer.getComponent(i)).getComponent(j)).equals(arg0.getSource())) {
						cal.set(Calendar.HOUR_OF_DAY, j);
						cal.set(Calendar.DAY_OF_WEEK, i);
						Date curDate = cal.getTime();
						System.out.println(curDate.getHours());
						AppointmentView appointmentView = new AppointmentView(MainFrame.getCurrentUser());
						JFrame appointmentFrame = new JFrame("CalTwenty - Avtalevisning");
						appointmentFrame.setPreferredSize(new Dimension(800,600));
						appointmentFrame.add(appointmentView);
						appointmentFrame.pack();
						appointmentFrame.setLocationRelativeTo(null);
						appointmentFrame.setAlwaysOnTop(true);
						appointmentFrame.setVisible(true);
						appointmentFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
						j = ((Container) dayContainer.getComponent(i)).getComponentCount();
						i = dayContainer.getComponentCount();
					}
				}
			}
			
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

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getPropertyName() == "BACK") {
				cal.lastWeek();
				currentWeek.setText("Uke " + cal.get(Calendar.WEEK_OF_YEAR));
				updateWeekDates();
				getUserCalFromServer();
			}
			else if (evt.getPropertyName() == "FORWARD") {
				cal.nextWeek();
				currentWeek.setText("Uke " + cal.get(Calendar.WEEK_OF_YEAR));
				updateWeekDates();
				getUserCalFromServer();
			}
		}
		
	}
	
	public JLabel getCurrentWeek() {
		return currentWeek;
	}

	public void setCurrentWeek(JLabel currentWeek) {
		this.currentWeek = currentWeek;
	}

	public void updateWeekDates() {
		String[] weekDates = cal.getWeekDates();
		System.out.println("TEST START");
		for(int i = 0; i < 7; i++) {
			weekDays[i].setText("<html>" + days[i].toString() + "<br>" + weekDates[i] + "</html>");
			weekDays[i].revalidate();
			weekDays[i].repaint();
			System.out.println(weekDays[i].getText());
		}
		System.out.println("TEST SLUTT");
	}
	
	public NorCalendar getCalendar() {
		return this.cal;
	}
	
	public void repaintCalendar(ArrayList toPaint) {
		for ( int j = 0; j < appointmentPanels.size(); j++) {
			try{
			remove(appointmentPanels.get(j));
			}
			catch(Exception e) {/* IGNORE */}
		}
		appointmentPanels = new ArrayList<TransparentPanel>();
		ArrayList<ArrayList> currentWeek = new ArrayList();
		for ( int i = 0; i < toPaint.size(); i++) {
			if ( ((Date) ((ArrayList)toPaint.get(i)).get(3)).after(cal.getTheFirstDayOfWeek()) && ((Date) ((ArrayList)toPaint.get(i)).get(3)).before(cal.getTheLastDayOfWeek())) {
				currentWeek.add((ArrayList)toPaint.get(i));
			}
		}
		
		int timeWidth = 35;
		int height = dayHeight/24;
		Date firstDayInWeek = cal.getTheFirstDayOfWeek();
		for ( int j = 0; j < currentWeek.size(); j++) {
			int dayOfWeek = cal.getDayOfWeek((Date) currentWeek.get(j).get(3));
			int startTime = ((Date) currentWeek.get(j).get(3)).getHours();
//			add(new TransparentPanel(currentWeek.get(j), dayOfWeek, startTime), new Integer(1));
			appointmentPanels.add(new TransparentPanel(currentWeek.get(j), dayOfWeek, startTime));
			
		}

		for(TransparentPanel panel:appointmentPanels) {
			add(panel, new Integer(1));
		}
		revalidate();
		repaint();
		
	}
	
	public void getUserCalFromServer() {
		Object[] msg = {"fetch", "kalender", MainFrame.getCurrentUser().getEmail()};
		Object rec = MainFrame.getClient().sendMsg(msg);
		userCal = (ArrayList<Object>) rec;
		System.out.println(userCal + " HER E MOTTAT KALENDER");
		
		repaintCalendar(userCal);
	}
	
	public class TransparentPanel extends JPanel implements MouseListener{
		private final Color dRed = new Color(255, 80, 80, 200);
		private final Color hRed = new Color(200, 100,100, 170);
		
		private ArrayList<Object> avtale;
		
		private int locationStartX;
		private int locationStartY;
		private int locationEndX;
		private int locationEndY;
		
		private int AVTALEID;
		
		public TransparentPanel(ArrayList<Object> avtale, int day, int time) {
			int timeWidth = 35;
			int height = dayHeight/24;
			this.avtale = avtale;
			Date firstDayInWeek = cal.getTheFirstDayOfWeek();
			((Container) dayContainer.getComponent(day)).getComponent(time).getX();
			int startX = (int) (dayContainer.getLocation().getX() + timeWidth);
			
			locationStartX = startX + 114*(day-1);
			int startY = (int) dayContainer.getLocation().getY();
			locationStartY = startY + (time) * height;
			this.avtale = avtale;
	    	setBackground(dRed);
	        setOpaque(false);
	        addMouseListener(this);
	        Date startDate = (Date) avtale.get(3);
	        int calStartX = dayContainer.getX();
	        calendarLayout.putConstraint(SpringLayout.WEST, this, locationStartX, SpringLayout.WEST, MainFrame.getCalendarView());
	        calendarLayout.putConstraint(SpringLayout.NORTH, this, locationStartY, SpringLayout.NORTH, MainFrame.getCalendarView());
	        JLabel description = new JLabel();
	        description.setText((String) avtale.get(6));
	        add(description);
	        setPreferredSize(new Dimension(115/2, dayHeight/24));
	        if (((Date) avtale.get(4)).getHours() > time || ((Date) avtale.get(4)).getDay() > day) {
	        	setPreferredSize(new Dimension(115/2, height*(24-time)));
	        }
	        
	        
	        
	    }
		@Override
	    public void paintComponent(Graphics g) {
	    	super.paintComponent(g);
	        g.setColor(getBackground());
	        Rectangle r = g.getClipBounds();
	        Graphics2D g2d = (Graphics2D) g;
	        g2d.drawString((String) avtale.get(0), 10, 10);
	        g.fillRect(r.x, r.y, r.width, r.height);
	    }
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO ÅPNE AVTALEVINDUE MED VALGT AVTALE
			System.out.println(avtale.get(7));
			Object[] msg = {"fetch", "specificappointment", Integer.parseInt((String) avtale.get(7))};
			ArrayList<Object> stuff = (ArrayList<Object>) MainFrame.getClient().sendMsg(msg);
			System.out.println("SJÅ HER____" + stuff);
			System.out.println(stuff.get(0)+ " MEJ E HER I DAG");
			Object[] msgGetPerson = {"fetch", "user", stuff.get(0)};
			Object[] rcv = (Object[]) MainFrame.getClient().sendMsg(msgGetPerson);
			String mottatNavn = (String) rcv[1];
			Appointment appointment = new Appointment(new Person((String) stuff.get(0), mottatNavn));
			appointment.setDescription((String) stuff.get(5));
			appointment.setStartTime((Date) stuff.get(3));
			appointment.setEndTime((Date) stuff.get(4));
			appointment.setLocation((String) stuff.get(6));
			try {
				MeetingRoom selMeeting = new MeetingRoom((String) stuff.get(9), (Integer) stuff.get(10));
				appointment.setMeetingRoom(selMeeting);
			} catch(Exception e) {
				System.err.println("No meeting rooms available");
			}
//			appointment.addParticipants(stuff.get(7));
			AppointmentView chosenApp = new AppointmentView(MainFrame.getCurrentUser(), appointment);
			AppointmentView appointmentView = new AppointmentView(MainFrame.getCurrentUser());
			JFrame appointmentFrame = new JFrame("CalTwenty - Avtalevisning");
			appointmentFrame.setPreferredSize(new Dimension(800,600));
			appointmentFrame.add(appointmentView);
			appointmentFrame.pack();
			appointmentFrame.setLocationRelativeTo(null);
			appointmentFrame.setAlwaysOnTop(true);
			appointmentFrame.setVisible(true);
			appointmentFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			
			
		}
		@Override
		public void mouseEntered(MouseEvent arg0) {
			setBackground(hRed);
			repaint();
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			
		}
		@Override
		public void mouseExited(MouseEvent arg0) {
			setBackground(dRed);
			repaint();
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
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
}
