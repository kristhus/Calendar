package visual;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import objects.Person;

public class RegistrationView extends JPanel{
	
	private MainFrame mainFrame;
	private LoginView loginView;
	private JButton nyBrukerButton;
	private CustomJTextField etternavnField;
	private CustomJTextField fornavnField;
	private CustomJTextField emailField;
	private CustomJTextField passordField;
	private CustomJTextField telefonnummerField;
	private RegistrationView thisView;
	private JFrame thisFrame;
	private JButton tilbakeTilLoginButton;

	
	public RegistrationView(MainFrame mainFrame, JFrame thisRegistrationFrame){
		this.mainFrame = mainFrame;
		thisFrame = thisRegistrationFrame;
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
		fornavnField = new CustomJTextField(new JTextField(), null, "Fornavn...");
		fornavnField.setPreferredSize(new Dimension(300, 30));
		add(fornavnField,c);
		
		c.gridy++; c.gridx=0;
		JLabel etternavnLabel = new JLabel("Etternavn");
		etternavnLabel.setPreferredSize(new Dimension(labelSizeX,30));
		add(etternavnLabel,c); c.gridx = 1;
		etternavnField = new CustomJTextField(new JTextField(), null, "Etternavn...");
		etternavnField.setPreferredSize(new Dimension(300, 30));
		add(etternavnField,c);
		
		c.gridx = 0; c.gridy++;
		JLabel emailLabel = new JLabel("E-mail");
		emailLabel.setPreferredSize(new Dimension(labelSizeX,30));
		add(emailLabel,c); c.gridx = 1;
		emailField = new CustomJTextField(new JTextField(), null, "E-mail address");
		emailField.setPreferredSize(new Dimension(300, 30));
		add(emailField,c);
		
		c.gridx = 0; c.gridy++;
		JLabel telefonnummerLabel = new JLabel("Telefonnummer");
		telefonnummerLabel.setPreferredSize(new Dimension(labelSizeX,30));
		add(telefonnummerLabel,c); c.gridx = 1;
		telefonnummerField = new CustomJTextField(new JTextField(), null, "Telefonnummer...");
		telefonnummerField.setPreferredSize(new Dimension(300, 30));
		add(telefonnummerField,c);
		
		c.gridx = 0; c.gridy++;
		JLabel passordLabel = new JLabel("Passord");
		passordLabel.setPreferredSize(new Dimension(labelSizeX,30));
		add(passordLabel,c); c.gridx = 1;
		passordField = new CustomJTextField(new JTextField(), null, "Passord...");
		passordField.setPreferredSize(new Dimension(300, 30));
		add(passordField,c);
		
		c.gridy++;
		nyBrukerButton = new JButton("Registrer ny bruker");
		nyBrukerButton.setPreferredSize(new Dimension(300, 30));
		nyBrukerButton.addActionListener(actionListener); nyBrukerButton.setName("nyBrukerButton");
		add(nyBrukerButton,c);
		
		c.gridy++;
		tilbakeTilLoginButton = new JButton("Tilbake til innlogging");
		tilbakeTilLoginButton.setPreferredSize(new Dimension(300, 30));
		tilbakeTilLoginButton.addActionListener(actionListener); tilbakeTilLoginButton.setName("tilbakeTilLoginButton");
		add(tilbakeTilLoginButton,c);
		
		c.gridx = 2;
		JLabel emptySpaceMakingLabel = new JLabel("");
		emptySpaceMakingLabel.setPreferredSize(new Dimension(labelSizeX,30));
		add(emptySpaceMakingLabel,c); 
	}
	
	private boolean checkInput(){
		if (fornavnField.getText().equals("")){
			return false;
		}
		if (etternavnField.getText().equals("")){
			return false;
		}
		if (emailField.getText().equals("")){
			return false;
		}
		if (telefonnummerField.getText().equals("")){
			return false;
		}
		if (passordField.getText().equals("")){
			return false;
		}
		return true;
	}
	
	private ActionListener actionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == nyBrukerButton){
				if (!checkInput()){
					createWarningFrame();
				}else{
					Person newUser = new Person((fornavnField.getText() + " " + etternavnField.getText()), emailField.getText(), Integer.parseInt(telefonnummerField.getText()));
					//TODO: KODE FOR Å LAGE NY BRUKER I DB, LOGGE INN OG SETTE BRUKER I MAIN
					mainFrame.createNewUserFromRegistrationView(newUser, passordField.getText());
					MainFrame.getLoginFrame().dispose();
					thisFrame.dispose();
				}
			}
			if (e.getSource() == tilbakeTilLoginButton){
				MainFrame.getLoginFrame().setVisible(true);
				thisFrame.dispose();
			}
			
			if (e.getSource() == warningOkButton){
				warningFrame.dispose();
			}
			
		}
		
		
		
	};
	private JButton warningOkButton;
	private JFrame warningFrame;
	
	private void createWarningFrame(){
		JPanel warningPanel = new JPanel();
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0; c.gridy=0;
		c.ipadx = 10; c.ipady = 10;
		warningPanel.add(new JLabel("Vennligst skriv inn korrekt data i alle feltene."),c);
		c.gridy++;
		warningOkButton = new JButton("Ok");
		warningOkButton.setPreferredSize(new Dimension(300, 30));
		warningOkButton.addActionListener(actionListener); warningOkButton.setName("warningOkButton");
		warningPanel.add(warningOkButton,c);
		warningFrame = new JFrame("Feil med inntastet data");
		warningFrame.setPreferredSize(new Dimension(550, 100));
		warningFrame.add(warningPanel);
		warningFrame.setLocationRelativeTo(null);
		warningFrame.setLocation(warningFrame.getX()-(thisFrame.getWidth()/2), warningFrame.getY());
        warningFrame.setAlwaysOnTop(true);
		warningFrame.pack();
		warningFrame.setVisible(true);
		warningFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

}

