package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import objects.Person;

public class LoginView extends JFrame{

	private MainFrame mainFrame;
	private JButton loginButton;
	private JButton nyBrukerButton;
	private JPanel loginPanel;
	private LoginView thisFrame;
	private CustomJTextField brukernavnField;
	private CustomJTextField passordField;

	public LoginView(MainFrame mainFrame){
		thisFrame = this;
		loginPanel = new JPanel();
		this.mainFrame = mainFrame;
		setLocation(mainFrame.splashScreen.getX()+50, mainFrame.splashScreen.getY()+50);
		setVisible(true);
		setAlwaysOnTop(true);
		mainFrame.splashScreen.setAlwaysOnTop(true);
		loginPanel.setBackground(Color.white);
		loginPanel.setVisible(true);
		loginPanel.setLayout(new GridBagLayout());
		add(loginPanel);
		setPreferredSize(new Dimension(550, 200));
		
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
		
		pack();
		setLocationRelativeTo(null);
	}

	private ActionListener actionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(loginButton)){
				//TODO: 1.GJØR HANDLINGER MED SERVER FOR Å SJEKKE OM LOGIN VAR SUCCSESSFULL.
				//TODO: 2.SETTE BRUKER I MAIN
				/////////////////////////////////////////TEST TEST TEST TEST TEST TEST /////////////////
				Person thomas = new Person("Thomas Mathisen", "samoth1601@gmail.com", 90048601);
				Object[] obj = {brukernavnField.getText(), passordField.getText()};
				Object[] toSend = {"fetch", "login", obj};
				mainFrame.getClient().sendMsg(toSend);
				mainFrame.logInAndSetUser(thomas);
				thisFrame.dispose();
			}
			else if (e.getSource() == nyBrukerButton){
				RegistrationView registrationView = new RegistrationView(mainFrame,thisFrame);
		        setVisible(false);
			}

		}
	};

}
