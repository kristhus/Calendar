package visual;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class AppointmentView extends JPanel implements PropertyChangeListener {
	private Appointment appointment;
	
	private JLabel name;
	private JLabel startDate;
	private JLabel endDate;
	private JLabel description;
	
	private JTextField namePC;
	private JSpinner startDatePC;
	private JSpinner endDatePC;
	private JTextArea descriptionPC;
	
	private SpringLayout layout;
	
	public AppointmentView() {
		appointment = new Appointment();
    	layout = new SpringLayout();
    	setLayout(layout);
		
		name = new JLabel("Avtalenavn: ");
		layout.putConstraint(SpringLayout.WEST, name, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, name, 50, SpringLayout.NORTH, this);
		add(name);
		
		namePC = new JTextField();
		namePC.setColumns(20);
		namePC.addKeyListener(new updateNameAction());
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
		startDatePC.addChangeListener(new updateStartDateAction());
		layout.putConstraint(SpringLayout.WEST, startDatePC, 5, SpringLayout.EAST, startDate);
		layout.putConstraint(SpringLayout.NORTH, startDatePC, 0, SpringLayout.NORTH, startDate);
		add(startDatePC);
		
		SpinnerDateModel model2 = new SpinnerDateModel(now.getTime(),
		earliestDate.getTime(), latestDate.getTime(), Calendar.WEEK_OF_YEAR );
		endDatePC = new JSpinner(model2);
		endDatePC.setEditor(new JSpinner.DateEditor(endDatePC, "dd-MM-yyyy HH:mm"));
		endDatePC.addChangeListener(new updateEndDateAction());
		layout.putConstraint(SpringLayout.WEST, endDatePC, 0, SpringLayout.WEST, startDatePC);
		layout.putConstraint(SpringLayout.NORTH, endDatePC, 0, SpringLayout.NORTH, endDate);
		add(endDatePC);
		
		description = new JLabel("Deskripsjon: ");
		layout.putConstraint(SpringLayout.WEST, description, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, description, 20, SpringLayout.SOUTH, endDate);
		add(description);
		
		descriptionPC = new JTextArea(8, 27); // (height, width)
		descriptionPC.getDocument().addDocumentListener(new updateDescriptionAction());
		descriptionPC.setLineWrap(true);
		layout.putConstraint(SpringLayout.WEST, descriptionPC, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, descriptionPC, 5, SpringLayout.SOUTH, description);
		add(descriptionPC);
	}
	
	class updateNameAction implements KeyListener {
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
	
	class updateStartDateAction implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			SpinnerDateModel model1 = (SpinnerDateModel) startDatePC.getModel();
			appointment.setStartTime(model1.getDate());
		}
	}
	
	class updateEndDateAction implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			SpinnerDateModel model = (SpinnerDateModel) endDatePC.getModel();
			appointment.setEndTime(model.getDate());
		}
	}
	
	class updateDescriptionAction implements DocumentListener {
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

	public void propertyChange(PropertyChangeEvent evt) {
		namePC.setText(appointment.getName());
		startDatePC.setValue(appointment.getStartTime());
		endDatePC.setValue(appointment.getEndTime());
		descriptionPC.setText(appointment.getDescription());
	}
}