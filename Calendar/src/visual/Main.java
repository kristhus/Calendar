package visual;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.WindowConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main extends JPanel {

    private static JFrame mainFrame; 

    private JPanel leftPanel;
    private JTextField searchField;

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
        mainFrame.add(new Main());
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    public Main() {
    	setPreferredSize(new Dimension(800, 600));
    	setBackground(Color.green);
		setVisible(true);
		setLayout(new BorderLayout(0,1));
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setVisible(true);
		JMenu menu;
		JMenuItem menuItem;
		
		menu = new JMenu("File");
		menu.getAccessibleContext().setAccessibleDescription("The menu for various shit");
		menu.setMnemonic(KeyEvent.VK_ALT);
		menuBar.add(menu);
		
		
		// New
		menuItem = new JMenuItem("New Appointment");
		menuItem.getAccessibleContext().setAccessibleDescription("Create new project and open the various windows");
		menuItem.setMnemonic(KeyEvent.VK_N);
		menuItem.setActionCommand("New Appointment");
		menu.add(menuItem);
		
		
		mainFrame.setJMenuBar(menuBar);
		
		add(createLeftWindow(), BorderLayout.WEST);
		
    }
    
    
    public JPanel createLeftWindow() {
    	leftPanel = new JPanel();
    	leftPanel.setBorder(BorderFactory.createRaisedBevelBorder());
    	SpringLayout springLayout = new SpringLayout();
    	leftPanel.setLayout(springLayout);
    	
    	searchField = new visual.CustomJTextField(new JTextField(), "/SEARCH.png", "Search..");
    	searchField.addKeyListener(new Listener());
    	
    	searchField.setPreferredSize(new Dimension(160, 30));
    	
    	
    	Container contentPane = mainFrame.getContentPane();
    	
    	JCheckBox cb1 = new JCheckBox();
        JLabel cb1Description = new JLabel();
        cb1Description.setText("Andre kalendere");
        
        JCheckBox cb2 = new JCheckBox();
        JLabel cb2Description = new JLabel();
        cb2Description.setText("TODO person.navn");
    	
        JButton nyAvtaleBtn = new JButton("Ny avtale");
        
        springLayout.putConstraint(SpringLayout.NORTH, nyAvtaleBtn, 5, SpringLayout.WEST, searchField);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, leftPanel, 100, SpringLayout.WEST, cb1Description);
        springLayout.putConstraint(SpringLayout.SOUTH, cb1, 100, SpringLayout.WEST, cb1Description);
        springLayout.putConstraint(SpringLayout.NORTH, leftPanel, 100, SpringLayout.WEST, cb1Description);
        springLayout.putConstraint(SpringLayout.NORTH, cb2, 100, SpringLayout.WEST, cb1Description);
        springLayout.putConstraint(SpringLayout.NORTH, leftPanel, 100, SpringLayout.WEST, cb1Description);
    	leftPanel.add(nyAvtaleBtn);

        leftPanel.add(searchField, SpringLayout.EAST);

        leftPanel.add(cb1Description);

        leftPanel.add(cb1);

        leftPanel.add(cb2Description);
        
        leftPanel.add(cb2);
        
        leftPanel.setPreferredSize(new Dimension(240, 800));
    	leftPanel.setVisible(true);
    	
    	return leftPanel;
    }
    
    
    public class CustomJTextField extends JTextField {
    	private Icon searchIcon;
    	
    }
    
    public class Listener implements ActionListener, KeyListener {
    	
    	@Override
    	public void actionPerformed(ActionEvent e) { // pre requisite that the events comes accomodated with an action command!
    		System.out.println("Action performed");
    		switch(e.getActionCommand()) {
    		case "New Appointment":
    			System.out.println("NEW APPOINTMENT CHOSEN");
    			break;
    		case "Something":
    			System.out.println("Chose something");
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
			switch(e.getKeyCode()) {
			case(KeyEvent.VK_ENTER):
				if (searchField.isFocusOwner()) {
					System.gc();
					JPanel searchPane = new JPanel();
					JScrollPane jsp = new JScrollPane();
					searchPane.setPreferredSize(new Dimension(200,400));
					searchPane.setVisible(true);
					searchPane.setBackground(Color.white);
					searchPane.setBorder(BorderFactory.createLineBorder(Color.black));
					searchPane.add(jsp);
					leftPanel.add(searchPane);
					mainFrame.repaint();
					
				}
				break;
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
    	
    	
    }

}