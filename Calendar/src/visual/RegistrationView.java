package visual;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RegistrationView extends JPanel{
	
	private MainFrame mainFrame;
	private JButton nyBrukerButton;
	
	public RegistrationView(MainFrame mainFrame){
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
		
		int labelSizeX = 100;
		
		JLabel fornavnLabel = new JLabel("Fornavn");
		fornavnLabel.setPreferredSize(new Dimension(labelSizeX,30));
		add(fornavnLabel,c); c.gridx = 1;
		CustomJTextField fornavnField = new CustomJTextField(new JTextField(), null, "Fornavn...");
		fornavnField.setPreferredSize(new Dimension(300, 30));
		add(fornavnField,c);
		
		c.gridy++; c.gridx=0;
		JLabel etternavnLabel = new JLabel("Etternavn");
		etternavnLabel.setPreferredSize(new Dimension(labelSizeX,30));
		add(etternavnLabel,c); c.gridx = 1;
		CustomJTextField etternavnField = new CustomJTextField(new JTextField(), null, "Etternavn...");
		etternavnField.setPreferredSize(new Dimension(300, 30));
		add(etternavnField,c);
		
		c.gridx = 0; c.gridy++;
		JLabel emailLabel = new JLabel("E-mail");
		emailLabel.setPreferredSize(new Dimension(labelSizeX,30));
		add(emailLabel,c); c.gridx = 1;
		CustomJTextField emailField = new CustomJTextField(new JTextField(), null, "E-mail address");
		emailField.setPreferredSize(new Dimension(300, 30));
		add(emailField,c);
		
		c.gridx = 0; c.gridy++;
		JLabel telefonnummerLabel = new JLabel("Telefonnummer");
		telefonnummerLabel.setPreferredSize(new Dimension(labelSizeX,30));
		add(telefonnummerLabel,c); c.gridx = 1;
		CustomJTextField telefonnummerField = new CustomJTextField(new JTextField(), null, "Telefonnummer...");
		telefonnummerField.setPreferredSize(new Dimension(300, 30));
		add(telefonnummerField,c);
		
		c.gridx = 0; c.gridy++;
		JLabel passordLabel = new JLabel("Passord");
		passordLabel.setPreferredSize(new Dimension(labelSizeX,30));
		add(passordLabel,c); c.gridx = 1;
		CustomJTextField passordField = new CustomJTextField(new JTextField(), null, "Passord...");
		passordField.setPreferredSize(new Dimension(300, 30));
		add(passordField,c);
		
		c.gridy++;
		nyBrukerButton = new JButton("Registrer ny bruker");
		nyBrukerButton.setPreferredSize(new Dimension(300, 30));
		nyBrukerButton.addActionListener(actionListener); nyBrukerButton.setName("nyBrukerButton");
		add(nyBrukerButton,c);
		
		c.gridx = 2;
		JLabel emptySpaceMakingLabel = new JLabel("");
		emptySpaceMakingLabel.setPreferredSize(new Dimension(labelSizeX,30));
		add(emptySpaceMakingLabel,c); 
	}
	
	private ActionListener actionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == nyBrukerButton){
				//TODO: KODE FOR Å LAGE NY BRUKER I DB, LOGGE INN OG SETTE BRUKER I MAIN
		        MainFrame.getLoginFrame().dispose();
			}
			
		}
	};
}

