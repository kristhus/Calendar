package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import objects.Person;
import serverConnection.Client;

public class LoginView extends JFrame{
	
	private MainFrame mainFrame;
	private JButton loginButton;
	private JButton nyBrukerButton;
	private JPanel loginPanel;
	private LoginView thisFrame;
	private CustomJTextField brukernavnField;
	private CustomJTextField passordField;
	
	private CustomJTextField ipField;
	private CustomJTextField portField;
	
	private JFrame config;

	public LoginView(MainFrame mainFrame){
		super("CalTwenty - Logg inn");
		thisFrame = this;
		loginPanel = new JPanel();
		this.mainFrame = mainFrame;
//		setLocation(mainFrame.splashScreen.getX()+50, mainFrame.splashScreen.getY()+50);
		setLocationRelativeTo(null);  // bytta ut ta der ^
		setVisible(true);
		setAlwaysOnTop(true);
		mainFrame.splashScreen.setAlwaysOnTop(true);
		loginPanel.setBackground(Color.white);
		loginPanel.setVisible(true);
		loginPanel.setLayout(new GridBagLayout());
		add(loginPanel);
		setPreferredSize(new Dimension(550, 230));

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0; c.gridy=0;
		c.ipadx = 25; c.ipady = 10;


		JLabel brukernavnLabel = new JLabel("Brukernavn");
		brukernavnLabel.setPreferredSize(new Dimension(60,30));
		loginPanel.add(brukernavnLabel,c); c.gridx = 1;
		brukernavnField = new CustomJTextField(new JTextField(), null, "Brukernavn...");
		brukernavnField.setPreferredSize(new Dimension(300, 30));
		loginPanel.add(brukernavnField,c);

		c.gridx = 0; c.gridy=1;
		JLabel passordLabel = new JLabel("Passord");
		passordLabel.setPreferredSize(new Dimension(60,30));
		loginPanel.add(passordLabel,c); c.gridx = 1;
		passordField = new CustomJTextField(new JTextField(), null, "Passord...");
		passordField.setPreferredSize(new Dimension(300, 30));
		loginPanel.add(passordField,c);

		c.gridy=2;
		loginButton = new JButton("Logg inn");
		loginButton.addActionListener(actionListener); loginButton.setName("loginButton");
		loginButton.setPreferredSize(new Dimension(300, 30));
		loginPanel.add(loginButton,c);

		c.gridy=3;
		nyBrukerButton = new JButton("Registrer ny bruker");
		nyBrukerButton.addActionListener(actionListener); nyBrukerButton.setName("nyBrukerButton");
		nyBrukerButton.setPreferredSize(new Dimension(300, 30));
		loginPanel.add(nyBrukerButton,c);

		c.gridx = 2;
		JLabel emptySpaceMakingLabel = new JLabel("");
		emptySpaceMakingLabel.setPreferredSize(new Dimension(60,30));
		loginPanel.add(emptySpaceMakingLabel,c); 

		setJMenuBar(createMenuBar());
		
		pack();
		setLocationRelativeTo(null);
	}
	
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Connection");
		JMenuItem serverName = new JMenuItem("Change connection settings");
		serverName.addActionListener(actionListener);
		serverName.setActionCommand("ClientConfig");
		menu.add(serverName);
		menuBar.add(menu);
		return menuBar;
	}
	private void createConnectionFrame() {
		config = new JFrame();
		config.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		config.setPreferredSize(new Dimension(300, 200));
		JPanel confPanel = new JPanel();
		confPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JLabel serverIP = new JLabel("Server IP ");
		JLabel serverPort = new JLabel("Server Port ");
		
		ipField = new CustomJTextField(new JTextField(), null, "IP-adresse..");
		portField = new CustomJTextField(new JTextField(), null, "Server port-number..");
		ipField.setText("129.241.127.115");
		portField.setText("8997");
		
		JButton apply = new JButton("Apply");
		apply.addActionListener(actionListener);
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(actionListener);
		
		c.gridx = 0; c.gridy = 0;
		
		confPanel.add(serverIP, c);
		c.gridx++; 
		confPanel.add(ipField, c);
		c.gridy ++; c.gridx = 0;
		confPanel.add(serverPort, c);
		c.gridx++;
		confPanel.add(portField, c);
		
		c.gridy ++; c.gridx = 0;
		confPanel.add(apply, c);
		c.gridx++;
		confPanel.add(cancel, c);
		
		config.add(confPanel);
		
		config.setVisible(true);
		config.setAlwaysOnTop(true);
		config.pack();
		config.setLocationRelativeTo(null);
	}

	private ActionListener actionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(loginButton)){
				//TODO: 1.GJØR HANDLINGER MED SERVER FOR Å SJEKKE OM LOGIN VAR SUCCSESSFULL.
				//TODO: 2.SETTE BRUKER I MAIN
				Client client = mainFrame.getClient();
				Object[] objectCredentials = {brukernavnField.getText(), passordField.getText()}; 
				Object[] toSend = {"fetch", "login", objectCredentials};
				try {
					Object[] personArray = (Object[]) (client.sendMsg(toSend));
					Person user = new Person((String)personArray[1], (String)personArray[0], (Integer)personArray[2]);
					mainFrame.logInAndSetUser(user);
					thisFrame.dispose();

				}
				catch (Exception x){
					x.printStackTrace();
					createWarningFrame();
				}
			}
			if (e.getActionCommand().equals("ClientConfig")) {
				if (config == null) {
					createConnectionFrame();
				}
			}
			else if (e.getSource() instanceof JButton) {
				JButton srcBtn = (JButton)e.getSource();
				switch(srcBtn.getActionCommand()) {
				case"Apply":
					try {
						int port = Integer.parseInt(portField.getText());
						MainFrame.getClient().setPort(port);
					} catch (Exception ex) {
						
					}
					MainFrame.getClient().setHostName(ipField.getText());
					config.dispose();
					config = null;
					break;
				case"Cancel":
					config.dispose();
					config = null;
					break;
					
				}
			}
			else if (e.getSource() == nyBrukerButton){
				RegistrationView registrationView = new RegistrationView(mainFrame,thisFrame);
				setVisible(false);
			}
			
			else if (e.getSource() == warningOkButton){
				warningFrame.dispose();
			}

		}
	};
	private JButton warningOkButton;
	private JFrame warningFrame;
	private void createWarningFrame(){
		JPanel warningPanel = new JPanel();
		warningPanel.setLayout(new GridBagLayout());
		warningPanel.setBackground(Color.white);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0; c.gridy=1;
		c.ipadx = 10; c.ipady = 10;
		warningPanel.add(new JLabel("Brukeren eksisterer ikke, eller passordet er feil."),c);
		c.gridy++;
		warningPanel.add(new JLabel("Vennligst prøv på nytt. "),c);
		c.gridy++;
		warningOkButton = new JButton("Ok");
		warningOkButton.setPreferredSize(new Dimension(300, 30));
		warningOkButton.addActionListener(actionListener); warningOkButton.setName("warningOkButton");
		warningPanel.add(warningOkButton,c);
		warningFrame = new JFrame("Feil med inntastet data");
		warningFrame.setPreferredSize(new Dimension(400, 125));
		warningFrame.add(warningPanel);
        warningFrame.setAlwaysOnTop(true);
		warningFrame.pack();
		warningFrame.setLocationRelativeTo(null);
		warningFrame.setVisible(true);
		warningFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

}
