package objects;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class ParticipantList extends JPanel implements PropertyChangeListener {
	private JScrollPane scrollPane;
	private JTable table;
	private Appointment appointment;
	private ParticipantModel model;
	private JLabel test;

	private String[] columnNames = {"Participant", "Remove"};
 	
	public ParticipantList(Appointment appointment) {
		this.appointment = appointment;

		model = new ParticipantModel(appointment.numberOfParticipants());
		table = new JTable(model);
		scrollPane = new JScrollPane(table);
		add(scrollPane);
	}

	public ParticipantModel getModel() {
		return model;
	}

	public class ParticipantModel extends AbstractTableModel {

		private int rows;
		private List<Boolean> rowList;
		private TreeSet<Integer> checked = new TreeSet<Integer>(Collections.reverseOrder());

		public ParticipantModel(int rows) {
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
				return ParticipantList.this.appointment.getParticipant(row).getName();
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

	public void propertyChange(PropertyChangeEvent e) {
		model = new ParticipantModel(appointment.numberOfParticipants());
		table.setModel(model);
		repaint();
	}
}