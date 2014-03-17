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

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
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

public class MainFrame extends JPanel {

    private static JFrame mainFrame;

	private static JFrame loginFrame; 

    private static JPanel leftPanel;
    private JPanel checkPanel;
    private JScrollPane checkScrollPane;
    private JTextField searchField;
    private JPanel searchPanel;
    private JScrollPane searchScrollPane;

	private Login_View loginView;
	
	private SpringLayout searchLayout;
    private final GridBagLayout searchResultGrid = new GridBagLayout();
    private GridBagConstraints searchRC;
    
    /* TEST */
    private ArrayList<Person> andreKalendere;
    private String[] testPersoner = {"KNUT", "KÅRE", "KYRRE", "AMANDA", "PedrO", "Jalapeno", "Trygvasson", "Kalle", "Kine", 
    		"Kristian", "Kerp", "Kevin", "Kjeks", "Kristina", "Kristine", "Kniseline", "Klars", "Kfryseboks","Kunstverk", "Kris", "Knut-kåre"};
    
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
        mainFrame.setVisible(true);
        
        
        
        /* DETTA E EN KOMMENTAR */
    }
    
    	
	

    private void initLoginViewAndFrame() {
    	loginView = new Login_View(this);
    	loginFrame = new JFrame();
        loginFrame.setPreferredSize(new Dimension(475, 200));
        loginFrame.add(loginView);
        loginFrame.pack();
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setAlwaysOnTop(true);
        loginFrame.setVisible(true);
	}

	public MainFrame() {
		initLoginViewAndFrame();
    	addMouseListener(new Listener());
    	setPreferredSize(new Dimension(800, 600));
    	setBackground(Color.white);
		setVisible(true);
		setLayout(new BorderLayout(0,1));
		
		add(createLeftWindow(), BorderLayout.WEST);
		add(new CalendarView());
    }
    
    
	public JPanel createLeftWindow() {
    	leftPanel = new JPanel();
    	leftPanel.setBorder(BorderFactory.createRaisedBevelBorder());
    	SpringLayout springLayout = new SpringLayout();
    	leftPanel.setLayout(springLayout);
    	leftPanel.setBackground(Color.white);

    	searchField = new visual.CustomJTextField(new JTextField(), "/SEARCH.png", "Search..");
    	searchField.addKeyListener(new Listener());
    	searchField.setActionCommand("search typer");
    	searchField.setPreferredSize(new Dimension(180, 30));
    	
    	Container container = new Container();
    	container.setLayout(new GridBagLayout());
    	GridBagConstraints c = new GridBagConstraints();
    	c.gridx = 0; c.gridy = 0; c.ipadx = 20;
    	
    	JCheckBox cb1 = new JCheckBox();
    	cb1.addActionListener(new Listener());
    	cb1.setActionCommand("Andre kalendere");
        JLabel cb1Description = new JLabel();
        cb1Description.setText("Andre kalendere");
        
        JButton nyAvtaleBtn = new JButton("Ny avtale");
        nyAvtaleBtn.setPreferredSize(new Dimension(235, 40));
        
        ImageIcon caret = new ImageIcon(this.getClass().getResource("/concat.png"));
        JButton searchDropDown = new JButton("Søk etter bruker");
        searchDropDown.setActionCommand("Search button");
        searchDropDown.setPreferredSize(new Dimension(180, 30));
        searchDropDown.addActionListener(new Listener());
        searchDropDown.setIcon(caret);
        searchDropDown.setHorizontalTextPosition(SwingConstants.LEFT);
        leftPanel.add(searchDropDown);
        

        
        createUserSearch();
        
        springLayout.putConstraint(SpringLayout.NORTH, searchDropDown, 40, SpringLayout.SOUTH, nyAvtaleBtn); // OK
        springLayout.putConstraint(SpringLayout.NORTH, container, 30, SpringLayout.SOUTH, searchDropDown); // OK
        springLayout.putConstraint(SpringLayout.NORTH, searchScrollPane, 0, SpringLayout.SOUTH, searchDropDown);

        container.add(cb1Description, c);
        c.gridx = 1;
        container.add(cb1, c);

    	leftPanel.add(nyAvtaleBtn);
    	leftPanel.add(searchScrollPane);
        leftPanel.add(container, SpringLayout.EAST);

        searchLayout = new SpringLayout();
        searchPanel.setLayout(searchLayout);
        searchPanel.add(searchField);
        searchLayout.putConstraint(SpringLayout.NORTH, searchField, 10, SpringLayout.NORTH, searchPanel);
        /* kommentar*/
        createPersonCheckPanel();
        springLayout.putConstraint(SpringLayout.NORTH, checkScrollPane, 50, SpringLayout.SOUTH, container);        
        leftPanel.add(checkScrollPane);
        leftPanel.setPreferredSize(new Dimension(240, 800));
    	leftPanel.setVisible(true);
    	
    	return leftPanel;
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
    

	public void createUserSearch() {
    	searchPanel = new JPanel();
    	searchPanel.setBackground(Color.white);
    	searchPanel.setVisible(true);
    	
    	searchScrollPane = new JScrollPane(searchPanel);
    	searchScrollPane.setBackground(Color.white);
    	searchScrollPane.setBorder(BorderFactory.createStrokeBorder(new BasicStroke()));
    	searchScrollPane.setPreferredSize(new Dimension(210,300));
    	searchScrollPane.setVisible(false);
    	searchScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }
    
    public class Listener implements ActionListener, KeyListener, MouseListener, PropertyChangeListener {
    	
    	@Override
    	public void actionPerformed(ActionEvent e) { // pre requisite that the events comes accomodated with an action command!
    		switch(e.getActionCommand()) {
    		case "Search button":
    			searchScrollPane.setVisible(!searchScrollPane.isVisible());
    			mainFrame.repaint();
    			break;
    		case "New Appointment":
    			System.out.println("NEW APPOINTMENT CHOSEN");
    			break;
    		case "Something":
    			System.out.println("Chose something");
    			break;
    		case "Andre kalendere":
    			checkScrollPane.setVisible(!checkScrollPane.isVisible());
    			break;
    		case "searchselection":
    			if( ((JCheckBox)e.getSource()).isSelected()) {
    				String dscrp = ((JLabel) ((Container) e.getSource()).getComponent(0)).getText();
    				if(!andreKalendere.contains(dscrp)){
    					JLabel cbDescription = new JLabel();
    					JCheckBox cb = new JCheckBox();
    					checkPanel.add(cbDescription);
    					checkPanel.add(cb);
    					andreKalendere.add((Person) e.getSource());
    				}
    				System.out.println(andreKalendere);
    			}
    			else {
    				// TODO FIND AND REMOVE THE SELECTED PERSON
    			}
    			break;
    		}
    	}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// Can not press and hold. This avoids stressing the server unnecessarily
			for(int p = 1; p<searchPanel.getComponentCount(); p++) {
				searchPanel.remove(p);
			}
			if(searchField.isFocusOwner() && !searchField.getText().equals("")) {
				String typed = searchField.getText();
				searchRC = new GridBagConstraints();
				searchRC.gridx = 0; searchRC.gridy = 0; 
			
				Container ct = new Container();
				ct.setLayout(searchResultGrid);
				for(int i = 0; i < testPersoner.length; i++) {
					boolean found = true;
					for(int j = 0; j < typed.length(); j++) {
						if(j > testPersoner[i].length()-1 || !(Character.toLowerCase(typed.charAt(j)) == Character.toLowerCase(testPersoner[i].charAt(j)))) {
							found = false;
							j=typed.length();
						}
					}
					if(found) {
						JLabel lab = new HoverLabel(new JLabel(testPersoner[i]), new Color(0,148,214), Color.white, Color.white, "/check.png");
						HoverLabel hLab = (HoverLabel) lab;
						hLab.addPropertyChangeListener(new Listener());
						hLab.setActionCommand("searchselection");
						System.out.println(searchPanel.getWidth());
						hLab.setPreferredSize(new Dimension(50, 16));
						searchRC.ipadx = searchPanel.getWidth()-50; 
						ct.add(hLab, searchRC);
						searchRC.gridy++;
						if(andreKalendere.contains(testPersoner[i])) {
							System.out.println("INNEHOLDER");
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

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			if(!searchScrollPane.isFocusOwner() && searchScrollPane.isVisible()) {
				searchScrollPane.setVisible(false);
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			// TODO Auto-generated method stub
			switch (evt.getPropertyName()) {
			case "searchselection" :
				// TODO
				if( (Boolean) evt.getNewValue()) {
					JLabel cbDescription = new JLabel(( (JLabel) evt.getSource()).getText());
					JCheckBox cb = new JCheckBox();
					checkPanel.add(cbDescription);
					checkPanel.add(cb);
					checkPanel.revalidate();
					searchPanel.revalidate();
				}
				else {
					String selectionName = ((JLabel) evt.getSource()).getText();
				}
				break;
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

    
}