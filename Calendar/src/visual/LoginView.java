package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class LoginView extends JPanel{

	public LoginView(){
		//setPreferredSize(new Dimension(800, 600));
		Color backgroundBlender = new Color (240,240,226);
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
		JButton loginButton = new JButton("Logg inn");
		loginButton.setPreferredSize(new Dimension(300, 30));
		add(loginButton,c);
		
		c.gridy=3;
		JButton nyBrukerButton = new JButton("Registrer ny bruker");
		nyBrukerButton.setPreferredSize(new Dimension(300, 30));
		add(nyBrukerButton,c);
		
		c.gridx = 2;
		JLabel emptySpaceMakingLabel = new JLabel("");
		emptySpaceMakingLabel.setPreferredSize(new Dimension(60,30));
		add(emptySpaceMakingLabel,c); 
	}
}
