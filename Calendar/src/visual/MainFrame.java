package visual;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.WindowConstants;

import calculations.NorCalendar;
import objects.Appointment;
import objects.Group;
import objects.MeetingRoom;
import objects.Participant;
import objects.Person;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainFrame extends JPanel {
	private ArrayList<Participant> testPersons = new ArrayList<Participant>();

	private static JFrame mainFrame;

	private static JFrame loginFrame; 

	private static CalendarView calendarView;
	
	private static Listener listener;
	
	private static JPanel miniCalendar;
	private static NorCalendar miniNorCalendar;
	private static JLabel miniCalendarMonth;
	private static Container miniCalendarDays;
	
	private static JPanel leftPanel;
	private JPanel checkPanel;
	private JScrollPane checkScrollPane;
	private Person currentUser;

	private LoginView loginView;
	
	private JButton newAppointmentButton;
	private DropDownSearch personSearch;
	
	private ArrayList<Person> andreKalendere;

	public static void main(String[] args) {

		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			try {
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			} catch (Exception ex) {}
		}



		mainFrame = new JFrame();
		mainFrame.setPreferredSize(new Dimension(1200, 800));
		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // TODO: Show confirmation dialogue about logging out!
		mainFrame.add(new MainFrame());
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
//		mainFrame.setResizable(false);

		mainFrame.revalidate();

	}


	private void initLoginViewAndFrame() {
		loginView = new LoginView(this);
		loginFrame = new JFrame();
		loginFrame.setPreferredSize(new Dimension(475, 200));
		loginFrame.add(loginView);
		loginFrame.pack();
		loginFrame.setLocationRelativeTo(null);
		loginFrame.setAlwaysOnTop(true);
		loginFrame.setVisible(true);
		loginFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	public MainFrame() {
		initLoginViewAndFrame();
		setPreferredSize(new Dimension(800, 600));
		setBackground(Color.white);
		setVisible(true);
		setLayout(new BorderLayout(0,1));

		calendarView = new CalendarView();
		add(createLeftWindow(), BorderLayout.WEST);
		add(calendarView);
	}

	public JPanel createLeftWindow() {
		listener = new Listener();
		
    	leftPanel = new JPanel();
    	leftPanel.setBorder(BorderFactory.createRaisedBevelBorder());
    	SpringLayout springLayout = new SpringLayout();
    	leftPanel.setLayout(springLayout);
    	leftPanel.setBackground(Color.white);
    	
        newAppointmentButton = new JButton("Ny avtale");
        newAppointmentButton.setActionCommand("New Appointment");
        newAppointmentButton.setPreferredSize(new Dimension(235, 40));
        newAppointmentButton.addActionListener(listener);
        leftPanel.add(newAppointmentButton);
        
        createMiniCalendar();
        leftPanel.add(miniCalendar);
        springLayout.putConstraint(SpringLayout.NORTH, miniCalendar, 30, SpringLayout.SOUTH, newAppointmentButton);
        springLayout.putConstraint(SpringLayout.WEST, miniCalendar, 25, SpringLayout.WEST, leftPanel);
    	
        JLabel cb1Description = new JLabel();
        cb1Description.setText("Andre kalendere");
        springLayout.putConstraint(SpringLayout.NORTH, cb1Description, 20, SpringLayout.SOUTH, miniCalendar);
        springLayout.putConstraint(SpringLayout.WEST, cb1Description, 10, SpringLayout.WEST, leftPanel);
        leftPanel.add(cb1Description);
    	JCheckBox cb1 = new JCheckBox();
    	cb1.addActionListener(listener);
    	cb1.setActionCommand("Andre kalendere");
        springLayout.putConstraint(SpringLayout.NORTH, cb1, 0, SpringLayout.NORTH, cb1Description);
        springLayout.putConstraint(SpringLayout.WEST, cb1, 10, SpringLayout.EAST, cb1Description);
    	leftPanel.add(cb1);
        
        createPersonCheckPanel();
        springLayout.putConstraint(SpringLayout.NORTH, checkScrollPane, 50, SpringLayout.SOUTH, miniCalendar);
        springLayout.putConstraint(SpringLayout.WEST, checkScrollPane, 10, SpringLayout.WEST, leftPanel);
        leftPanel.add(checkScrollPane);
        leftPanel.setPreferredSize(new Dimension(240, 800));
    	leftPanel.setVisible(true);
    	
    	initializeTestPersons();
    	
    	personSearch = new DropDownSearch("Søk etter bruker", null, testPersons);
    	// springLayout.putConstraint(SpringLayout.NORTH, personSearch, , arg3, cb1Description);
    	
    	return leftPanel;
    }
	
    public void initializeTestPersons() {
    	testPersons.add(new Person("Torfinn", "email", 123));
    	testPersons.add(new Person("Kristian", "annen email", 321));
    	testPersons.add(new Person("Thomas", "stuff", 0));
    	testPersons.add(new Group("Gruppe 20", "facebook"));
    }

	
	public void createMiniCalendar() {
		miniNorCalendar = new NorCalendar();
				
		miniCalendar = new JPanel();
		miniCalendar.setBackground(Color.white);
		miniCalendar.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		
		Container labels = new Container();
		labels.setLayout(new BorderLayout());
		
		JLabel left = new JLabel("<");
		left.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		left.setPreferredSize(new Dimension(20,12));
		left.setBackground(new Color(215,150,0));
		left.setBorder(BorderFactory.createRaisedBevelBorder());
		left.addMouseListener(listener);
		labels.add(left, BorderLayout.WEST);
		
		miniCalendarMonth = new JLabel();
		int curMonth = miniNorCalendar.MONTH;
		miniCalendarMonth.setText(miniNorCalendar.month(curMonth) + " - " + Integer.toString(miniNorCalendar.YEAR) );
		miniCalendarMonth.setHorizontalAlignment(JLabel.CENTER);
		labels.add(miniCalendarMonth, BorderLayout.CENTER);
		
		JLabel right = new JLabel(" >");
		right.setBorder(BorderFactory.createRaisedBevelBorder());
		right.setPreferredSize(new Dimension(20,12));
		right.setBackground(new Color(215,150,0)); // TODO Velg en bra farge
		right.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		right.addMouseListener(listener);
		labels.add(right, BorderLayout.EAST);
		miniCalendar.add(labels, c);
		
		c.gridx = 0;
		c.gridy++;
		Container header = createHeader();
		miniCalendar.add(header, c);
		
		c.gridy++;
		miniCalendarDays = new Container();
		miniCalendar.add(updateDaysOfMonth(), c);
	}
	
	
    public static Container updateDaysOfMonth() {
    	GridBagConstraints c = new GridBagConstraints();
    	c.gridx = 0; c.gridy = 0;
    	
    	miniCalendarMonth.setText(miniNorCalendar.month(miniNorCalendar.MONTH) + " - " + miniNorCalendar.YEAR);
		miniCalendarDays.removeAll();
		miniCalendarDays.setLayout(new GridBagLayout());
		int firstDay = miniNorCalendar.getFirstDayOfMonth();
		int lastDay = miniNorCalendar.getLastDayOfMonth();
		
		int lastDayLastWeekLastMonth = miniNorCalendar.getLastDayOfLastWeekInLastMonth();
		
		int nextMonthDayCounter = 1;
		int dayCounter = 0;
		for (int i = 0; i < 6; i++) {
			JPanel week = new JPanel();
			week.setBackground(Color.white);
			week.setLayout(new GridBagLayout());
			week.addMouseListener(listener);
			week.setName(Integer.toString(i));
			for (int j = 0; j< 7; j++) {
				JPanel day = new JPanel();
				
				day.setBackground(Color.white);
				day.setBorder(BorderFactory.createLineBorder(Color.black));
				day.setVisible(true);
				JLabel dateOfDay = new JLabel();
				dateOfDay.setPreferredSize(new Dimension(16,14));
				dateOfDay.setName("black");
				dateOfDay.setVisible(true);
				if ( (i == 0 && j >= firstDay) || (i > 0 && dayCounter < lastDay) ) {
					dayCounter++;
					if(dayCounter < 10) { // #SJÅFINTUT
						dateOfDay.setText(Integer.toString(dayCounter));
					}
					else {
						dateOfDay.setText(Integer.toString(dayCounter));
					}
				}
				else if (j < firstDay && i < 2) {
					dateOfDay.setText(Integer.toString(lastDayLastWeekLastMonth-firstDay+j+1));
					dateOfDay.setName("gray");
					dateOfDay.setForeground(Color.gray);
				}
				else {
					dateOfDay.setText(" " + Integer.toString(nextMonthDayCounter));
					dateOfDay.setName("gray");
					dateOfDay.setForeground(Color.gray);
					nextMonthDayCounter++;
				}
				day.add(dateOfDay);
					week.add(day);
			}
			for(int empty = 0; empty < 7; empty++) {
				if( ((JLabel) ((JPanel) week.getComponent(empty)).getComponent(0)).getText() != " " && nextMonthDayCounter < 8) {
					miniCalendarDays.add(week, c);
					c.gridy ++;		
				}
			}
		}
		miniCalendar.revalidate();
		return miniCalendarDays;
    }
	
	private Container createHeader() {
		Container header = new Container();
		header.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0;
		for(int d = 0; d < 7; d++) {
			c.gridx++;
			JPanel headerDayPanel = new JPanel();
			headerDayPanel.setBackground(new Color(255,208,112));
			headerDayPanel.setBorder(BorderFactory.createLineBorder(Color.black));
			JLabel dayDescription = new JLabel( Character.toString(CalendarView.days[d].toString().charAt(0)) ); // TODO GET THIS DATE
			dayDescription.setPreferredSize(new Dimension(16,14));
			headerDayPanel.add(dayDescription);
			header.add(headerDayPanel);
		}
		return header;
	}

	public void createPersonCheckPanel() {
		andreKalendere = new ArrayList<Person>();
		checkPanel = new JPanel();
		checkPanel.setPreferredSize(new Dimension(220, 400));
		checkPanel.setLayout(new FlowLayout());
		checkPanel.setBackground(Color.lightGray);
		checkPanel.setBorder(BorderFactory.createLoweredSoftBevelBorder());
		checkPanel.setVisible(true);

		checkScrollPane = new JScrollPane(checkPanel);
		//    	checkScrollPane.getHorizontalScrollBar().setUnitIncrement(16);
		checkScrollPane.setVisible(false);
		checkScrollPane.setPreferredSize(new Dimension(230,200));
	}


	public void logInAndSetUser(Person user) {
		this.currentUser=user;
		mainFrame.setVisible(true);
		System.out.println(currentUser.getName());
	}

	public void createNewUserFromRegistrationView(Person user,String password){
		this.currentUser=user;
		mainFrame.setVisible(true);
		System.out.println(currentUser.getName());
		//TODO: KODE FOR Å LAGE NY BRUKER I DB OSV OSV OSV
	}

	public class Listener implements ActionListener, MouseListener {

		@Override
		public void actionPerformed(ActionEvent e) { // pre requisite that the events comes accomodated with an action command!
			switch(e.getActionCommand()) {
			case "New Appointment":
				Appointment appointment = new Appointment(currentUser);
				appointment.setName("derp");
				Calendar startDate = Calendar.getInstance();
				startDate.set(2000, 8, 12, 14, 7, 9);
				Calendar endDate = Calendar.getInstance();
				endDate.set(2004, 12, 3, 22, 57, 17);
				appointment.setStartTime(startDate.getTime());
				appointment.setEndTime(endDate.getTime());
				appointment.setDescription("Stuff");
				appointment.setMeetingRoom(new MeetingRoom("Rom 13", 13));
				appointment.addParticipant(new Person("En kar", "epost"));
				appointment.addParticipant(new Person("Annen kar", "fisk"));
				
				System.out.println("NEW APPOINTMENT CHOSEN");
				AppointmentView appointmentView = new AppointmentView(currentUser, appointment);
				JFrame appointmentFrame = new JFrame();
				appointmentFrame.setPreferredSize(new Dimension(800,600));
				appointmentFrame.add(appointmentView);
				appointmentFrame.pack();
				appointmentFrame.setLocationRelativeTo(null);
				appointmentFrame.setAlwaysOnTop(true);
				appointmentFrame.setVisible(true);
				appointmentFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				break;
			case "Something":
				System.out.println("Chose something");
				break;
			case "Andre kalendere":
				checkScrollPane.setVisible(!checkScrollPane.isVisible());
				break;
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() instanceof JLabel) {
				if( ((JLabel) e.getSource()).getText() == "<") {
					miniNorCalendar.monthBack();
					miniCalendarDays = updateDaysOfMonth();
				}
				else {
					miniNorCalendar.monthForward();
					miniCalendarDays = updateDaysOfMonth();
				}
			}
			if (e.getSource() instanceof JPanel) {
				String name = ((JPanel) e.getSource()).getName();
				int weekOfMonth = Integer.parseInt(name);
				System.out.println(name);
				calendarView.getCalendar().setTime(miniNorCalendar.getTime());
				calendarView.getCalendar().setWeek(weekOfMonth);
				updateDaysOfMonth();
				calendarView.updateWeekDates();
				System.out.println("weekdays: " + calendarView.getCalendar().getWeekDates()[0]);
				System.out.println("source char: " + ((JLabel)((JPanel)((JPanel) e.getSource()).getComponent(0)).getComponent(0)).getText());
				try {
				if(calendarView.getCalendar().getWeekDates()[0].charAt(0) != ((JLabel)((JPanel)((JPanel) e.getSource()).getComponent(0)).getComponent(0)).getText().charAt(0)
						|| (calendarView.getCalendar().getWeekDates()[0].charAt(1) != ((JLabel)((JPanel)((JPanel) e.getSource()).getComponent(0)).getComponent(0)).getText().charAt(1) 
							&& ((JLabel)((JPanel)((JPanel) e.getSource()).getComponent(0)).getComponent(0)).getText().length() < 3 )) {
					calendarView.getCalendar().nextWeek();
					updateDaysOfMonth();
					calendarView.updateWeekDates();
				}
				}
				catch(IndexOutOfBoundsException exc) {
					System.out.println("sum ting wong");
				}
//				if (weekOfMonth == 0) { // For whatever reason, this must be done twice. I suspect it is because of the Calendar interprets 0 as the last week in last month, instead of first this month
//					updateDaysOfMonth();
//					calendarView.updateWeekDates();
//				}
				calendarView.getCurrentWeek().setText("Uke " + calendarView.getCalendar().get(Calendar.WEEK_OF_YEAR));
				
			}
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() instanceof JLabel) {
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			if (e.getSource() instanceof JPanel) {
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				for (int i = 0; i < ((JPanel) e.getSource()).getComponentCount(); i++) {
					((JPanel)((JPanel) e.getSource()).getComponent(i)).setBackground(new Color(0,200,255));
					((JLabel)((JPanel)((JPanel) e.getSource()).getComponent(i)).getComponent(0)).setForeground(Color.white);
				}
			}
		}
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() instanceof JLabel) {
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			if (e.getSource() instanceof JPanel) {
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				for (int i = 0; i < ((JPanel) e.getSource()).getComponentCount(); i++) {
					((JPanel)((JPanel) e.getSource()).getComponent(i)).setBackground(Color.white);
					if(	((JLabel)((JPanel)((JPanel) e.getSource()).getComponent(i)).getComponent(0)).getName() == "black") {
						((JLabel)((JPanel)((JPanel) e.getSource()).getComponent(i)).getComponent(0)).setForeground(Color.black);
					}
					else {
						((JLabel)((JPanel)((JPanel) e.getSource()).getComponent(i)).getComponent(0)).setForeground(Color.gray);
					}
				}
			}
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			/*
			if(!searchScrollPane.isFocusOwner() && searchScrollPane.isVisible()) {
				searchScrollPane.setVisible(false);
			}
			*/
			if (e.getSource() instanceof JLabel) {
				((JLabel) e.getSource()).setBorder(BorderFactory.createLoweredBevelBorder());
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() instanceof JLabel) {
				((JLabel) e.getSource()).setBorder(BorderFactory.createRaisedBevelBorder());
			}
		}
	}

	
	public static JFrame getMainFrame() {
		return mainFrame;
	}

	public static JFrame getLoginFrame() {
		return loginFrame;
	}

	public static JPanel getLeftPanel() {
		return leftPanel;
	}


    public static CalendarView getCalendarView() {
    	return calendarView;
    }
    
    public static void setMiniCalendarDays(Container cont) {
    	miniCalendarDays = cont;
    }



}