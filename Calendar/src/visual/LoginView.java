package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JFrame;

public class LoginView extends JPanel{
	
	private MainFrame mainFrame;
	private JButton loginButton;
	private JButton nyBrukerButton;

	public LoginView(MainFrame mainFrame){
		this.mainFrame = mainFrame;
		//setPreferredSize(new Dimension(800, 600));
		Color backgroundBlender = new Color (241,240,226);
		setBackground(backgroundBlender);
		setBackground(Color.white);
		setVisible(true);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0; c.gridy=0;
		c.ipadx = 10; c.ipady = 10;
		
		
		JLabel brukernavnLabel = new JLabel("Brukernavn");
		brukernavnLabel.setPreferredSize(new Dimension(60,30));
		add(brukernavnLabel,c); c.gridx = 1;
		CustomJTextField brukernavnField = new CustomJTextField(new JTextField(), null, "Brukernavn...");
		brukernavnField.setPreferredSize(new Dimension(300, 30));
		add(brukernavnField,c);
		
		c.gridx = 0; c.gridy=1;
		JLabel passordLabel = new JLabel("Passord");
		passordLabel.setPreferredSize(new Dimension(60,30));
		add(passordLabel,c); c.gridx = 1;
		CustomJTextField passordField = new CustomJTextField(new JTextField(), null, "Passord...");
		passordField.setPreferredSize(new Dimension(300, 30));
		add(passordField,c);
		
		c.gridy=2;
		loginButton = new JButton("Logg inn");
		loginButton.addActionListener(actionListener); loginButton.setName("loginButton");
		loginButton.setPreferredSize(new Dimension(300, 30));
		add(loginButton,c);
		
		c.gridy=3;
		nyBrukerButton = new JButton("Registrer ny bruker");
		nyBrukerButton.addActionListener(actionListener); nyBrukerButton.setName("nyBrukerButton");
		nyBrukerButton.setPreferredSize(new Dimension(300, 30));
		add(nyBrukerButton,c);
		
		c.gridx = 2;
		JLabel emptySpaceMakingLabel = new JLabel("");
		emptySpaceMakingLabel.setPreferredSize(new Dimension(60,30));
		add(emptySpaceMakingLabel,c); 
	}
	
	private ActionListener actionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(loginButton)){
				//TODO: 1.GJØR HANDLINGER MED SERVER FOR Å SJEKKE OM LOGIN VAR SUCCSESSFULL.
				//TODO: 2.SETTE BRUKER I MAIN
				MainFrame.getLoginFrame().dispose();
			}
			else if (e.getSource() == nyBrukerButton){
				RegistrationView registrationView = new RegistrationView(mainFrame);
		    	JFrame registrationFrame = new JFrame();
		        registrationFrame.setPreferredSize(new Dimension(550, 300));
		        //loginFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); 
		        registrationFrame.add(registrationView);
		        registrationFrame.pack();
		        registrationFrame.setLocationRelativeTo(null);
		        registrationFrame.setAlwaysOnTop(true);
		        registrationFrame.setVisible(true);
		        MainFrame.getLoginFrame().dispose();
			}
			
		}
	};
}
