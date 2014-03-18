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
import objects.Person;
import objects.Searchable;

public class DropDownSearch extends JPanel {
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
    
    
    public DropDownSearch(String buttonName, Appointment appointment, ArrayList<? extends Searchable> searchObjects) {
    	this.appointment = appointment;
    	this.searchObjects = (ArrayList<Searchable>) searchObjects;
    	
		searchLayout = new SpringLayout();
		setLayout(searchLayout);

    	searchField = new visual.CustomJTextField(new JTextField(), "/SEARCH.png", "Search..");
    	searchField.addKeyListener(new SearchListener());
    	searchField.setActionCommand("search typer");
    	searchField.setPreferredSize(new Dimension(180, 30));
    	// participantListView.add(searchField);
    	
    	
        ImageIcon caret = new ImageIcon(this.getClass().getResource("/concat.png"));
        searchDropDown = new JButton(buttonName);
        searchDropDown.setActionCommand("Search button");
        searchDropDown.setPreferredSize(new Dimension(180, 30));
        searchDropDown.addActionListener(new SearchListener());
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
    	searchScrollPane.setPreferredSize(new Dimension(210,300));
    	searchScrollPane.setVisible(false);
    	searchScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    	add(searchScrollPane);
    	
        searchLayout.putConstraint(SpringLayout.NORTH, searchScrollPane, 0, SpringLayout.SOUTH, searchDropDown);
        
        checkPanel = new JPanel();
        checkPanel.setPreferredSize(new Dimension(220, 400));
        checkPanel.setLayout(new FlowLayout());
        checkPanel.setBackground(Color.lightGray);
        checkPanel.setBorder(BorderFactory.createLoweredSoftBevelBorder());
        checkPanel.setVisible(true);
        add(checkPanel);
        
    	checkScrollPane = new JScrollPane(checkPanel);
    	// checkScrollPane.getHorizontalScrollBar().setUnitIncrement(16);
    	checkScrollPane.setVisible(false);
    	checkScrollPane.setPreferredSize(new Dimension(230,200));
    	add(checkScrollPane);
    	setPreferredSize(new Dimension(240, 600));
    	setVisible(true);
    }
    
    
    
	class SearchListener implements ActionListener, KeyListener, PropertyChangeListener {

		public void keyPressed(KeyEvent arg0) {
			// nothing happens
		}

		// when user types a letter in the search field
		public void keyReleased(KeyEvent arg0) {
			// Can not press and hold. This avoids stressing the server unnecessarily
			for(int p = 1; p<searchPanel.getComponentCount(); p++) {
				searchPanel.remove(p);
			}
			if(searchField.isFocusOwner() && !searchField.getText().equals("")) {
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
					if(found) {
						JLabel lab = new HoverLabel( searchObjects.get(i), new Color(0,148,214), Color.white, Color.white, "/check.png");
						HoverLabel hLab = (HoverLabel) lab;
						hLab.addPropertyChangeListener(new SearchListener());
						hLab.setActionCommand("searchselection");
						System.out.println(searchPanel.getWidth());
						hLab.setPreferredSize(new Dimension(50, 16));
						searchRC.ipadx = searchPanel.getWidth()-50; 
						ct.add(hLab, searchRC);
						searchRC.gridy++;
						if(appointment.isParticipating((Person) searchObjects.get(i))) {
							hLab.setSelected(true);
						}
					}
				}
				searchLayout.putConstraint(SpringLayout.NORTH, ct, 20, SpringLayout.SOUTH, searchPanel.getComponent(searchPanel.getComponentCount()-1));
				searchPanel.add(ct);
			}
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
					JLabel cbDescription = new JLabel(( (JLabel) e.getSource()).getText());
					JCheckBox cb = new JCheckBox();
					checkPanel.add(cbDescription);
					checkPanel.add(cb);
					checkPanel.revalidate();
					searchPanel.revalidate();

					HoverLabel label = (HoverLabel) e.getSource();
					appointment.addParticipant((Person) label.getObject());
				}
				else {
					HoverLabel label = (HoverLabel) e.getSource();
					appointment.removeParticipant((Person) label.getObject());
				}
				break;
			}
		}
	}
}