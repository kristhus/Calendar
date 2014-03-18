package visual;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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

import visual.ParticipantList.ParticipantModel;

public class AppointmentView extends JPanel implements PropertyChangeListener {
	private MainFrame mainFrame;
	
	private Appointment appointment;
	private ArrayList<Person> testPersons = new ArrayList<Person>();
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
    private MeetingRoomTableModel meetingRoomTableModel;
    private JTable meetingRoomTable;
    private JScrollPane meetingRoomList;
    // MeetingRoomPanel ends here
    
	public AppointmentView(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		
		appointment = new Appointment();
		appointment.addParticipant(new Person("Ole", "vet ikke", 21));
		appointment.addParticipant(new Person("Robin", "stuff", 234));
		appointment.addParticipant(new Person("Trond", null, 12345678));
		appointment.addPropertyChangeListener(this);
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
		
		SpinnerDateModel model1 = new SpinnerDateModel(now.getTime(),
		earliestDate.getTime(), latestDate.getTime(), Calendar.WEEK_OF_YEAR );
		startDatePC = new JSpinner(model1);
		startDatePC.setEditor(new JSpinner.DateEditor(startDatePC, "dd-MM-yyyy HH:mm"));
		startDatePC.addChangeListener(new UpdateStartDateAction());
		layout.putConstraint(SpringLayout.WEST, startDatePC, 5, SpringLayout.EAST, startDate);
		layout.putConstraint(SpringLayout.NORTH, startDatePC, 0, SpringLayout.NORTH, startDate);
		add(startDatePC);
		
		SpinnerDateModel model2 = new SpinnerDateModel(now.getTime(),
		earliestDate.getTime(), latestDate.getTime(), Calendar.WEEK_OF_YEAR );
		endDatePC = new JSpinner(model2);
		endDatePC.setEditor(new JSpinner.DateEditor(endDatePC, "dd-MM-yyyy HH:mm"));
		endDatePC.addChangeListener(new UpdateEndDateAction());
		layout.putConstraint(SpringLayout.WEST, endDatePC, 0, SpringLayout.WEST, startDatePC);
		layout.putConstraint(SpringLayout.NORTH, endDatePC, 0, SpringLayout.NORTH, endDate);
		add(endDatePC);
		
		description = new JLabel("Deskripsjon: ");
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
    	
		participantSearch = new DropDownSearch("Legg til deltaker", appointment, testPersons);
    	layout.putConstraint(SpringLayout.WEST, participantSearch, 0, SpringLayout.WEST, participantTablePane);
    	layout.putConstraint(SpringLayout.NORTH, participantSearch, 20, SpringLayout.SOUTH, participantTablePane);
		add(participantSearch);
    	
    	removeSelectedParticipants = new JButton("Fjern valgte deltakere");
    	removeSelectedParticipants.addActionListener(new RemoveParticipantsAction());
    	layout.putConstraint(SpringLayout.EAST, removeSelectedParticipants, 0, SpringLayout.EAST, participantTablePane);
    	layout.putConstraint(SpringLayout.NORTH, removeSelectedParticipants, 0, SpringLayout.NORTH, participantSearch);
    	add(removeSelectedParticipants);
    	
    	initializeTestPersons();
    	
    	// MeetingRoomPanel begins here
    	place = new JLabel("Sted: ");
    	layout.putConstraint(SpringLayout.WEST, place, 5, SpringLayout.WEST, this);
    	layout.putConstraint(SpringLayout.NORTH, place, 350, SpringLayout.NORTH, this);
    	add(place);
    	
    	placePC = new JTextField();
    	placePC.setColumns(20);
    	layout.putConstraint(SpringLayout.WEST, placePC, 10, SpringLayout.EAST, place);
    	layout.putConstraint(SpringLayout.NORTH, placePC, 0, SpringLayout.NORTH, place);
    	add(placePC);
    	
    	chooseFromList = new JLabel("Velg fra liste");
    	layout.putConstraint(SpringLayout.WEST, chooseFromList, 0, SpringLayout.WEST, place);
    	layout.putConstraint(SpringLayout.NORTH, chooseFromList, 15, SpringLayout.SOUTH, place);
    	add(chooseFromList);
    	
    	chooseFromListPC = new JCheckBox();
    	layout.putConstraint(SpringLayout.WEST, chooseFromListPC, 10, SpringLayout.EAST, chooseFromList);
    	layout.putConstraint(SpringLayout.NORTH, chooseFromListPC, 0, SpringLayout.NORTH, chooseFromList);
    	add(chooseFromListPC);
    	
    	minCapacity = new JLabel("Min. romkapasitet");
    	layout.putConstraint(SpringLayout.WEST, minCapacity, 0, SpringLayout.WEST, place);
    	layout.putConstraint(SpringLayout.NORTH, minCapacity, 15, SpringLayout.SOUTH, chooseFromList);
    	add(minCapacity);
    	
    	minCapacityPC = new JTextField();
    	minCapacityPC.setColumns(2);
    	layout.putConstraint(SpringLayout.WEST, minCapacityPC, 10, SpringLayout.EAST, minCapacity);
    	layout.putConstraint(SpringLayout.NORTH, minCapacityPC, 0, SpringLayout.NORTH, minCapacity);
    	add(minCapacityPC);
    	
    	initializeTestRooms();
    	
    	meetingRoomTableModel = new MeetingRoomTableModel(testRooms, 0);
    	meetingRoomTable = new JTable(meetingRoomTableModel);
    	meetingRoomList = new JScrollPane(meetingRoomTable);
    	layout.putConstraint(SpringLayout.WEST, meetingRoomList, 0, SpringLayout.WEST, minCapacity);
    	layout.putConstraint(SpringLayout.NORTH, meetingRoomList, 20, SpringLayout.SOUTH, minCapacity);
    	meetingRoomList.setPreferredSize(new Dimension(200, 200));
    	add(meetingRoomList);
    	
    	// MeetingRoomPanel ends here
	}
	
	
    public void initializeTestPersons() {
    	testPersons.add(new Person("Torfinn", "email", 123));
    	testPersons.add(new Person("Kristian", "annen email", 321));
    	testPersons.add(new Person("Thomas", "stuff", 0));
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
	
	class UpdateStartDateAction implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			SpinnerDateModel model1 = (SpinnerDateModel) startDatePC.getModel();
			appointment.setStartTime(model1.getDate());
		}
	}
	
	class UpdateEndDateAction implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			SpinnerDateModel model = (SpinnerDateModel) endDatePC.getModel();
			appointment.setEndTime(model.getDate());
		}
	}
	
	class UpdateDescriptionAction implements DocumentListener {
		public void changedUpdate(DocumentEvent e) {
			// is never fired for text-only documents
		}

		public void insertUpdate(DocumentEvent e) {
			Document document = descriptionPC.getDocument();
			try {
				appointment.setDescription(document.getText(0, document.getLength()));
			}
			catch (BadLocationException fuck) {
				System.out.println("Fuck");
			}
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
				System.out.println("stuffstuffstuff " + element);
				appointment.removeParticipant(element.intValue());
			}
		}
	}

	public void propertyChange(PropertyChangeEvent e) {
		/*
		namePC.setText(appointment.getName());
		startDatePC.setValue(appointment.getStartTime());
		endDatePC.setValue(appointment.getEndTime());
		descriptionPC.setText(appointment.getDescription());
		*/
		participantTableModel = new ParticipantTableModel(appointment.numberOfParticipants());
		participantTable.setModel(participantTableModel);
		repaint();
	}
	
	public class ParticipantTableModel extends AbstractTableModel {
		
		private int rows;
		private List<Boolean> rowList;
		private TreeSet<Integer> checked = new TreeSet<Integer>(Collections.reverseOrder());
		private String[] columnNames = {"Deltaker", "Velg"};
		
		public ParticipantTableModel(int rows) {
			this.rows = rows;
			rowList = new ArrayList<Boolean>(rows);
			for (int i = 0; i < rows; i++) {
				rowList.add(Boolean.FALSE);
			}
		}
		
		public int getColumnCount() {
			return 2;
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
			return col == 1;
		}
		
		public TreeSet<Integer> getCheckedRows() {
			return checked;
		}
	}
	
	private class MeetingRoomTableModel extends AbstractTableModel {
		private ArrayList<MeetingRoom> meetingRooms = new ArrayList<MeetingRoom>();
		private String[] columnNames = {"Møterom" , "Kapasistet"};
		
		public MeetingRoomTableModel(ArrayList<MeetingRoom> testRooms, int capacity) {
			for (int i = 0; i < testRooms.size(); i++) {
				MeetingRoom meetingRoom = testRooms.get(i);
				if (meetingRoom.getCapacity() >= capacity) {
					meetingRooms.add(meetingRoom);
				}
			}
		}
		
		public int getColumnCount() {
			return 2;
		}

		public int getRowCount() {
			return meetingRooms.size();
		}

		public Object getValueAt(int row, int col) {
			if (col == 0) {
				return meetingRooms.get(row).getName();
			}
			else {
				return meetingRooms.get(row).getCapacity();
			}
		}
		
		public String getColumnName(int col) {
			return columnNames[col];
		}
		
		public Class<?> getColumnClass(int col) {
			return getValueAt(0, col).getClass();
		}
	}
}