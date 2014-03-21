package visual;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import objects.Appointment;
import objects.Group;
import objects.MeetingRoom;
import objects.Participant;
import objects.ParticipantList;
import objects.Person;

public class AppointmentView extends JPanel implements PropertyChangeListener {
	private Appointment appointment;
	private ArrayList<Participant> testPersons = new ArrayList<Participant>();
	private ArrayList<MeetingRoom> testRooms = new ArrayList<MeetingRoom>();

	private JLabel name;
	private JLabel startDate;
	private JLabel endDate;
	private JLabel description;

	private JTextField namePC;
	private JSpinner startDatePC;
	private JSpinner endDatePC;
	private JTextArea descriptionPC;
	
	private SpringLayout layout;
    
    private DropDownSearch participantSearch;
    private ParticipantList participantList;
    private JButton removeSelectedParticipants;
    
	private ParticipantTableModel participantTableModel;
	private JTable participantTable;
	private JScrollPane participantTablePane;
    
    // MeetingRoomPanel begins here
    private JLabel place;
    private JTextField placePC;
    private JLabel chooseFromList;
    private JCheckBox chooseFromListPC;
    private JLabel minCapacity;
    private JTextField minCapacityPC;
    private DropDownSearch meetingRoomSearch;
    // MeetingRoomPanel ends here
    
    private JButton saveButton;
    
    public ArrayList<Object> getMeetingRooms() {
    	Object[] msg = {"fetch", "ressurs", null};
    	return (ArrayList<Object>) MainFrame.getClient().sendMsg(msg);
    }
    
	public AppointmentView(Person user) {
		System.out.println(getMeetingRooms());
		appointment = new Appointment(user);
		appointment.addPropertyChangeListener(this);
		
		initializeAppointmentView(user);
	}
	
	public AppointmentView(Person user, Date startTime, Date endTime) {
		appointment = new Appointment(user);
		appointment.setStartTime(startTime);
		appointment.setEndTime(endTime);
		appointment.addPropertyChangeListener(this);
		
		initializeAppointmentView(user);
	}
	
	public AppointmentView(Person user, Appointment appointment) {
		this.appointment = appointment;
		appointment.addPropertyChangeListener(this);
		
		initializeAppointmentView(user);
	}
	
	public void initializeAppointmentView(Person user) {
    	layout = new SpringLayout();
    	setLayout(layout);

		name = new JLabel("Avtalenavn: ");
		layout.putConstraint(SpringLayout.WEST, name, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, name, 30, SpringLayout.NORTH, this);
		add(name);

		namePC = new JTextField();
		namePC.setColumns(20);
		namePC.addKeyListener(new UpdateNameAction());
		layout.putConstraint(SpringLayout.WEST, namePC, 5, SpringLayout.EAST, name);
		layout.putConstraint(SpringLayout.NORTH, namePC, 0, SpringLayout.NORTH, name);
		add(namePC);

		startDate = new JLabel("Starttidspunkt: ");
		layout.putConstraint(SpringLayout.WEST, startDate, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, startDate, 20, SpringLayout.SOUTH, name);
		add(startDate);

		endDate = new JLabel("Sluttidspunkt: ");
		layout.putConstraint(SpringLayout.WEST, endDate, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, endDate, 20, SpringLayout.SOUTH, startDate);
		add(endDate);

		Calendar now = Calendar.getInstance();
		Calendar earliestDate = Calendar.getInstance();
		earliestDate.set(1980, 1, 1, 0, 0, 0);
		Calendar latestDate = Calendar.getInstance();
		latestDate.set(2099, 12, 12, 23, 59, 59);

		SpinnerDateModel startDateModel = new SpinnerDateModel(now.getTime(),
		earliestDate.getTime(), latestDate.getTime(), Calendar.WEEK_OF_YEAR );
		startDatePC = new JSpinner(startDateModel);
		startDatePC.setEditor(new JSpinner.DateEditor(startDatePC, "dd-MM-yyyy HH:mm"));
		startDatePC.addChangeListener(new UpdateDateAction());
		layout.putConstraint(SpringLayout.WEST, startDatePC, 5, SpringLayout.EAST, startDate);
		layout.putConstraint(SpringLayout.NORTH, startDatePC, 0, SpringLayout.NORTH, startDate);
		add(startDatePC);

		SpinnerDateModel endDateModel = new SpinnerDateModel(now.getTime(),
		earliestDate.getTime(), latestDate.getTime(), Calendar.WEEK_OF_YEAR );
		endDatePC = new JSpinner(endDateModel);
		endDatePC.setEditor(new JSpinner.DateEditor(endDatePC, "dd-MM-yyyy HH:mm"));
		endDatePC.addChangeListener(new UpdateDateAction());
		layout.putConstraint(SpringLayout.WEST, endDatePC, 0, SpringLayout.WEST, startDatePC);
		layout.putConstraint(SpringLayout.NORTH, endDatePC, 0, SpringLayout.NORTH, endDate);
		add(endDatePC);

		description = new JLabel("Beskrivelse: ");
		layout.putConstraint(SpringLayout.WEST, description, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, description, 20, SpringLayout.SOUTH, endDate);
		add(description);

		descriptionPC = new JTextArea(8, 27); // (height, width)
		descriptionPC.getDocument().addDocumentListener(new UpdateDescriptionAction());
		descriptionPC.setLineWrap(true);
		layout.putConstraint(SpringLayout.WEST, descriptionPC, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, descriptionPC, 5, SpringLayout.SOUTH, description);
		add(descriptionPC);
    	
		participantTableModel = new ParticipantTableModel(appointment.numberOfParticipants());
		participantTable = new JTable(participantTableModel);
		participantTablePane = new JScrollPane(participantTable);
		participantTablePane.setPreferredSize(new Dimension(400, 300));
    	layout.putConstraint(SpringLayout.WEST, participantTablePane, 350, SpringLayout.WEST, this);
    	layout.putConstraint(SpringLayout.NORTH, participantTablePane, 0, SpringLayout.NORTH, name);
    	add(participantTablePane);
    	
		participantSearch = new DropDownSearch("Legg til deltager", appointment, testPersons);
    	layout.putConstraint(SpringLayout.WEST, participantSearch, 0, SpringLayout.WEST, participantTablePane);
    	layout.putConstraint(SpringLayout.NORTH, participantSearch, 20, SpringLayout.SOUTH, participantTablePane);
		add(participantSearch);
    	
    	removeSelectedParticipants = new JButton("Fjern valgte deltagere");
    	removeSelectedParticipants.addActionListener(new RemoveParticipantsAction());
    	layout.putConstraint(SpringLayout.EAST, removeSelectedParticipants, 0, SpringLayout.EAST, participantTablePane);
    	layout.putConstraint(SpringLayout.NORTH, removeSelectedParticipants, 0, SpringLayout.NORTH, participantSearch);
    	add(removeSelectedParticipants);
    	
    	appointment.addPropertyChangeListener(participantSearch);
    	
    	initializeTestPersons();
    	
    	// MeetingRoomPanel begins here
    	place = new JLabel("Sted: ");
    	layout.putConstraint(SpringLayout.WEST, place, 5, SpringLayout.WEST, this);
    	layout.putConstraint(SpringLayout.NORTH, place, 320, SpringLayout.NORTH, this);
    	add(place);
    	
    	placePC = new JTextField();
    	placePC.setColumns(20);
    	placePC.addKeyListener(new UpdatePlaceAction());
    	layout.putConstraint(SpringLayout.WEST, placePC, 10, SpringLayout.EAST, place);
    	layout.putConstraint(SpringLayout.NORTH, placePC, 0, SpringLayout.NORTH, place);
    	add(placePC);
    	
    	chooseFromList = new JLabel("Velg fra liste");
    	layout.putConstraint(SpringLayout.WEST, chooseFromList, 0, SpringLayout.WEST, place);
    	layout.putConstraint(SpringLayout.NORTH, chooseFromList, 15, SpringLayout.SOUTH, place);
    	add(chooseFromList);
    	
    	chooseFromListPC = new JCheckBox();
    	chooseFromListPC.addItemListener(new UpdateCheckBoxAction());
    	layout.putConstraint(SpringLayout.WEST, chooseFromListPC, 5, SpringLayout.EAST, chooseFromList);
    	layout.putConstraint(SpringLayout.NORTH, chooseFromListPC, 0, SpringLayout.NORTH, chooseFromList);
    	add(chooseFromListPC);
    	
    	minCapacityPC = new JTextField();
    	minCapacityPC.setColumns(2);
    	minCapacityPC.addKeyListener(new UpdateCapacityAction());
    	layout.putConstraint(SpringLayout.EAST, minCapacityPC, 0, SpringLayout.EAST, placePC);
    	layout.putConstraint(SpringLayout.NORTH, minCapacityPC, 0, SpringLayout.NORTH, chooseFromList);
    	add(minCapacityPC);
    	
    	minCapacity = new JLabel("Min. romkapasitet");
    	layout.putConstraint(SpringLayout.EAST, minCapacity, -5, SpringLayout.WEST, minCapacityPC);
    	layout.putConstraint(SpringLayout.NORTH, minCapacity, 0, SpringLayout.NORTH, minCapacityPC);
    	add(minCapacity);
    	
    	initializeTestRooms();
    	
    	meetingRoomSearch = new DropDownSearch("Velg møterom", appointment, testRooms);
    	meetingRoomSearch.setEnabled(false);
    	layout.putConstraint(SpringLayout.WEST, meetingRoomSearch, 0, SpringLayout.WEST, chooseFromList);
    	layout.putConstraint(SpringLayout.NORTH, meetingRoomSearch, 20, SpringLayout.SOUTH, chooseFromList);
    	add(meetingRoomSearch);
    	
    	appointment.addPropertyChangeListener(meetingRoomSearch);
    	
    	// MeetingRoomPanel ends here
    	
    	saveButton = new JButton("Lagre endringer");
    	saveButton.addActionListener(new SaveAppointmentAction());
    	layout.putConstraint(SpringLayout.EAST, saveButton, -20, SpringLayout.EAST, this);
    	layout.putConstraint(SpringLayout.SOUTH, saveButton, -20, SpringLayout.SOUTH, this);
    	add(saveButton);
    	
    	if (appointment.getMeetingRoom() != null) {
    		MeetingRoom meetingRoom = appointment.getMeetingRoom();
    		chooseFromListPC.setSelected(true);
    		appointment.setMeetingRoom(meetingRoom);
    	}
    	
		propertyChange(null);
		
		if (! user.equals(appointment.getAppointmentOwner())) {
			disableEditing();
		}
	}
	
	
	public void disableEditing() {
		namePC.setEditable(false);
		startDatePC.setEnabled(false);
		endDatePC.setEnabled(false);
		descriptionPC.setEditable(false);
		placePC.setEditable(false);
		minCapacityPC.setEditable(false);
		participantSearch.setEnabled(false);
		removeSelectedParticipants.setEnabled(false);
		remove(saveButton);
	}

    public void initializeTestPersons() {
    	testPersons.add(new Person("Torfinn", "email", 123));
    	testPersons.add(new Person("Kristian", "annen email", 321));
    	testPersons.add(new Person("Thomas", "stuff", 0));
    	testPersons.add(new Group("Gruppe 20", "facebook"));
    }
    
    public void initializeTestRooms() {
    	testRooms.add(new MeetingRoom("P15", 50));
    	testRooms.add(new MeetingRoom("R97", 20));
    	testRooms.add(new MeetingRoom("R34", 12));
    	testRooms.add(new MeetingRoom("Bøttekott", 4));
    	testRooms.add(new MeetingRoom("Billig motellrom", 2));
    }
    

	class UpdateNameAction implements KeyListener {
		public void keyPressed(KeyEvent e) {
			// No action performed
		}
		public void keyReleased(KeyEvent e) {
			appointment.setName(namePC.getText());
		}
		public void keyTyped(KeyEvent e) {
			// No action performed
		}
	}

	class UpdateDateAction implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			SpinnerDateModel startDateModel = (SpinnerDateModel) startDatePC.getModel();
			
			if (e.getSource().equals(startDatePC)) {
				appointment.setStartTime(startDateModel.getDate());
			}
			else if (e.getSource().equals(endDatePC)) {
				SpinnerDateModel endDateModel = (SpinnerDateModel) endDatePC.getModel();
				appointment.setEndTime(endDateModel.getDate());
			}
		}
	}

	class UpdateDescriptionAction implements DocumentListener {
		public void changedUpdate(DocumentEvent e) {
			// is never fired for text-only documents
		}

		public void insertUpdate(DocumentEvent e) {
			Runnable doUpdate = new Runnable() {
				public void run() {
					Document document = descriptionPC.getDocument();
					try {
						String text = document.getText(0, document.getLength());
						appointment.setDescription(text);
					}
					catch (BadLocationException fuck) {
						System.out.println("Fuck");
					}
				}
			};
			SwingUtilities.invokeLater(doUpdate);
		}

		public void removeUpdate(DocumentEvent e) {
			insertUpdate(e);
		}
	}

	class RemoveParticipantsAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			TreeSet<Integer> checkedRows = participantTableModel.getCheckedRows();

			Iterator<Integer> iterator = checkedRows.iterator();
			while (iterator.hasNext()) {
				Integer element = iterator.next();
				appointment.removeParticipant(element.intValue());
			}
		}
	}
	
	class UpdatePlaceAction implements KeyListener {
		public void keyPressed(KeyEvent e) {
			// Nothing happens
		}

		public void keyReleased(KeyEvent e) {
			appointment.setLocation(placePC.getText());
		}

		public void keyTyped(KeyEvent e) {
			// Nothing happens
		}
	}
	
	class UpdateCapacityAction implements KeyListener {
		public void keyPressed(KeyEvent arg0) {
			// Nothing happens
		}

		public void keyReleased(KeyEvent arg0) {
    		String minCap = minCapacityPC.getText();
    		boolean isNumber = true;
    		for (int i = 0; i < minCap.length(); i++) {
    			if (! Character.isDigit(minCap.charAt(i))) {
    				isNumber = false;
    				break;
    			}
    		}
    		if (isNumber && minCap.length() > 0) {
    			appointment.setMinCapacity(Integer.parseInt(minCap));
    		}
    		else {
    			appointment.setMinCapacity(0);
    		}
		}

		public void keyTyped(KeyEvent arg0) {
			// Nothing happens
		}
	}
	
	class UpdateCheckBoxAction implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			appointment.setLocation(null);
			appointment.setMeetingRoom(null);
			meetingRoomSearch.setEnabled(chooseFromListPC.isSelected());
			placePC.setEditable(! chooseFromListPC.isSelected());
		}
	}
	
	public void updateTableBackground() {
		for(int i = 0; i < participantTable.getComponentCount(); i++) {
			if (i %2 == 0) {
				participantTable.getComponent(i).setBackground(Color.gray);
//				((JLabel) pariticipantTable.getComponent(i))
			}
		}
	}
	
	class SaveAppointmentAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String validityStatus = appointment.validityStatus();
			
			if (! validityStatus.equals("valid")) {
				JOptionPane.showMessageDialog(saveButton, validityStatus, "Feil oppstod", JOptionPane.ERROR_MESSAGE);
			}
			
			// rekkefølge: startTid, sluttTid, beskrivelse, sted, avtaleeier
			
			Object[] felter = {new Timestamp(appointment.getStartTime().getTime()), new Timestamp(appointment.getEndTime().getTime()), appointment.getDescription(), appointment.getLocation(), appointment.getAppointmentOwner().getEmail()};
			Object[] msg = {"store", "appointment", felter};
			MainFrame.getClient().sendMsg(msg);
		}
	}

	public void propertyChange(PropertyChangeEvent e) {
		if (appointment.getName() != null) {
			namePC.setText(appointment.getName());
		}
		if (appointment.getStartTime() != null) {
			startDatePC.setValue(appointment.getStartTime());
		}
		if (appointment.getEndTime() != null) {
			endDatePC.setValue(appointment.getEndTime());
		}
		if (appointment.getDescription() != null) {
			descriptionPC.setText(appointment.getDescription());
		}
		
		if (chooseFromListPC.isSelected()) {
			if (appointment.getMeetingRoom() != null && appointment.getMeetingRoom().getName() != null) {
				placePC.setText(appointment.getMeetingRoom().getName());
			}
			else {
				placePC.setText("");
			}
		}
		else {
			placePC.setText(appointment.getLocation());
		}
		
		participantTableModel = new ParticipantTableModel(appointment.numberOfParticipants());
		participantTable.setModel(participantTableModel);
		repaint();
	}

	public class ParticipantTableModel extends AbstractTableModel {
		private int rows;
		private List<Boolean> rowList;
		private TreeSet<Integer> checked = new TreeSet<Integer>(Collections.reverseOrder());
		private String[] columnNames = {"Deltager", "Status", "Velg"};

		public ParticipantTableModel(int rows) {
			this.rows = rows;
			rowList = new ArrayList<Boolean>(rows);
			for (int i = 0; i < rows; i++) {
				rowList.add(Boolean.FALSE);
			}
		}

		public int getColumnCount() {
			return 3;
		}

		public int getRowCount() {
			return rows;
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		public Object getValueAt(int row, int col) {
			if (col == 0) {
				return appointment.getParticipant(row).getName();
			}
			else if (col == 1) {
				Boolean bool = appointment.getStatus(appointment.getParticipant(row));
				if (bool == null) {
					return "Ikke svart";
				}
				else if (bool) {
					return "Kommer";
				}
				else {
					return "Kommer ikke";
				}
			}
			else {
				return rowList.get(row);
			}
		}

		public void setValueAt(Object value, int row, int col) {
			boolean b = (Boolean) value;
			rowList.set(row, b);
			if (b) {
				checked.add(row);
			}
			else {
				checked.remove(row);
			}
		}

		public Class<?> getColumnClass(int col) {
			return getValueAt(0, col).getClass();
		}

		public boolean isCellEditable(int row, int col) {
			return col == 2;
		}

		public TreeSet<Integer> getCheckedRows() {
			return checked;
		}
	}
}