package visual;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import objects.Appointment;
import objects.MeetingRoom;
import objects.Participant;
import objects.Searchable;

public class DropDownSearch extends JPanel implements PropertyChangeListener {
	private ArrayList<Searchable> searchObjects;

	private Appointment appointment;

	private JTextField searchField;
	private JButton searchDropDown;
	private JPanel checkPanel;
	private JScrollPane checkScrollPane;
	private JPanel searchPanel;
	private JScrollPane searchScrollPane;

	private SpringLayout searchLayout;

	private GridBagConstraints searchRC;
	private final GridBagLayout searchResultGrid = new GridBagLayout();

	private SearchListener searchListener;
	private boolean calledFromMainFrame;
	private ArrayList<Participant>markedUsers;
	private DropDownSearch thisView;

	public DropDownSearch(String buttonText, Appointment appointment, ArrayList<? extends Searchable> searchObjects,Boolean isMainFrameView) {
		markedUsers = new ArrayList<Participant>();
		calledFromMainFrame = isMainFrameView;
		this.appointment = appointment;
		this.searchObjects = (ArrayList<Searchable>) searchObjects;
		searchListener = new SearchListener();

		searchLayout = new SpringLayout();
		setLayout(searchLayout);

		searchField = new visual.CustomJTextField(new JTextField(), "/SEARCH.png", "Search..");
		searchField.addKeyListener(searchListener);
		searchField.setActionCommand("search typer");
		searchField.setPreferredSize(new Dimension(180, 30));
		// participantListView.add(searchField);


		ImageIcon caret = new ImageIcon(this.getClass().getResource("/caret.png"));
		searchDropDown = new JButton(buttonText);
		searchDropDown.setActionCommand("Search button");
		searchDropDown.setPreferredSize(new Dimension(180, 30));
		searchDropDown.addActionListener(searchListener);
		searchDropDown.setIcon(caret);
		searchDropDown.setHorizontalTextPosition(SwingConstants.LEFT);
		add(searchDropDown);

		searchPanel = new JPanel();
		searchPanel.setBackground(Color.white);
		searchPanel.setVisible(true);
		searchPanel.setLayout(searchLayout);
		searchPanel.add(searchField);
		add(searchPanel);

		searchScrollPane = new JScrollPane(searchPanel);
		searchScrollPane.setBackground(Color.white);
		searchScrollPane.setBorder(BorderFactory.createStrokeBorder(new BasicStroke()));
		searchScrollPane.setPreferredSize(new Dimension(210,200));
		searchScrollPane.setVisible(false);
		searchScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		searchLayout.putConstraint(SpringLayout.NORTH, searchScrollPane, 0, SpringLayout.SOUTH, searchDropDown);
		add(searchScrollPane);

		setPreferredSize(new Dimension(240, 600));
		setVisible(true);
	}

	public void setEnabled(boolean enabled) {
		searchDropDown.setEnabled(enabled);
	}

	public void propertyChange(PropertyChangeEvent e) {
		System.out.println("I'm listening");
		searchListener.keyReleased(null);
	}



	private class SearchListener implements ActionListener, KeyListener, PropertyChangeListener {



		public void keyPressed(KeyEvent arg0) {
			// nothing happens
		}

		// when user types a letter in the search field
		public void keyReleased(KeyEvent arg0) {
			// Can not press and hold. This avoids stressing the server unnecessarily
			for(int p = 1; p<searchPanel.getComponentCount(); p++) {
				searchPanel.remove(p);
			}
			String typed = searchField.getText();
			searchRC = new GridBagConstraints();
			searchRC.gridx = 0; 
			searchRC.gridy = 0; 

			Container ct = new Container();
			ct.setLayout(searchResultGrid);
			for(int i = 0; i < searchObjects.size(); i++) {
				boolean found = true;
				for(int j = 0; j < typed.length(); j++) {
					if(j > searchObjects.get(i).getName().length()-1 || !(Character.toLowerCase(typed.charAt(j)) == Character.toLowerCase(searchObjects.get(i).getName().charAt(j)))) {
						found = false;
						j=typed.length();
					}
				}
				if (searchObjects.get(i) instanceof MeetingRoom) {
					MeetingRoom meetingRoom = (MeetingRoom) searchObjects.get(i);
					if (meetingRoom.getCapacity() < appointment.getMinCapacity()) {
						found = false;
					}
				}
				if(found) {
					JLabel lab = new HoverLabel(searchObjects.get(i), new Color(0,148,214), Color.white, Color.white, "/check.png");
					HoverLabel hLab = (HoverLabel) lab;
					hLab.addPropertyChangeListener(new SearchListener());
					hLab.setActionCommand("searchselection");
					hLab.setPreferredSize(new Dimension(50, 16));
					searchRC.ipadx = searchPanel.getWidth()-50; 
					ct.add(hLab, searchRC);
					searchRC.gridy++;
					if (searchObjects.get(i) instanceof Participant) {
						if(appointment.isParticipating((Participant) searchObjects.get(i))) {
							hLab.setSelected(true);
						}
					}
				}
			}
			searchLayout.putConstraint(SpringLayout.NORTH, ct, 20, SpringLayout.SOUTH, searchPanel.getComponent(searchPanel.getComponentCount()-1));
			searchPanel.add(ct);
			searchPanel.revalidate();
			searchPanel.repaint();
			searchScrollPane.revalidate();
		}

		public void keyTyped(KeyEvent arg0) {
			// nothing happens
		}

		// when user presses the search drop-down button
		public void actionPerformed(ActionEvent e) {
			switch(e.getActionCommand()) {
			case ("Search button"):
				searchScrollPane.setVisible(!searchScrollPane.isVisible());
			keyReleased(null);
			break;
			}
		}

		// when user selects an item from the search list
		public void propertyChange(PropertyChangeEvent e) {
			// TODO Auto-generated method stub
			switch (e.getPropertyName()) {
			case "searchselection" :
				// TODO
				if( (Boolean) e.getNewValue()) {
					searchPanel.revalidate();
					if (calledFromMainFrame){
						HoverLabel label = (HoverLabel) e.getSource();
						if (label.getObject() instanceof Participant) {
							markedUsers.add((Participant) label.getObject());
							MainFrame.updateOtherCalendarsToShow(markedUsers);
						}

					}else{
						HoverLabel label = (HoverLabel) e.getSource();
						if (label.getObject() instanceof Participant) {
							appointment.addParticipant((Participant) label.getObject());
						}
						else if (label.getObject() instanceof MeetingRoom) {
							appointment.setMeetingRoom((MeetingRoom) label.getObject());
						}
					}
				}
				else {
					if (calledFromMainFrame){
						HoverLabel label = (HoverLabel) e.getSource();
						if (label.getObject() instanceof Participant) {
							markedUsers.remove((Participant) label.getObject());
							MainFrame.updateOtherCalendarsToShow(markedUsers);
						}
					}else{
						HoverLabel label = (HoverLabel) e.getSource();
						if (label.getObject() instanceof Participant) {
							appointment.removeParticipant((Participant) label.getObject());
						}
						else if (label.getObject() instanceof MeetingRoom) {
							appointment.setMeetingRoom(null);
						}
					}
				}
				break;
			}
		}
	}
}