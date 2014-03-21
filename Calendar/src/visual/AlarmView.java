package visual;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import objects.Appointment;

public class AlarmView extends JFrame{
	
	int fo = 8;
	private JButton warningOkButton;
	private JFrame thisFrame;

	public AlarmView(Appointment appointment) {
		super("CalTwenty - Alarmvisning");
		thisFrame = this;
		JPanel alarmPanel = new JPanel();
		alarmPanel.setLayout(new GridBagLayout());
		alarmPanel.setBackground(Color.white);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0; c.gridy=1;
		c.ipadx = 10; c.ipady = 10;
		alarmPanel.add(new JLabel("Dette er en alarm for avtalen: " + appointment.getName()),c);
		c.gridy++;
		alarmPanel.add(new JLabel("Starter: "+ appointment.getStartTime().toString()),c);
		c.gridy++;
		warningOkButton = new JButton("Ok");
		warningOkButton.setPreferredSize(new Dimension(300, 30));
		warningOkButton.addActionListener(actionListener); warningOkButton.setName("warningOkButton");
		alarmPanel.add(warningOkButton,c);
		setPreferredSize(new Dimension(400, 125));
		add(alarmPanel);
        setAlwaysOnTop(true);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
	}

	private ActionListener actionListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
	};
	
}
