package visual;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import objects.Appointment;
import objects.Person;

public class NotificationView extends JFrame{

	private JButton seAvtaleButton;
	private JFrame thisFrame;

	public NotificationView (MainFrame mainFrame, Appointment appointment){
		super("Nytt Varsel");
		thisFrame = this;
		JPanel notificationPanel = new JPanel();
		
		//notificationPanel.setPreferredSize(new Dimension(800, 600));
		notificationPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0; c.gridy=0;
		c.ipadx = 10; c.ipady = 3;
		notificationPanel.add(new JLabel("Du har en ny invitasjon!"),c);
		c.gridy++;
		notificationPanel.add(new JLabel(appointment.getAppointmentOwner().getName() + " har invitert deg til en avtalen: " + appointment.getName() + "."),c);
		
		c.gridy++;
		notificationPanel.add(new JLabel("Dato:\t" + appointment.getStartTime().getDate() + "." + appointment.getStartTime().getMonth()+"."+appointment.getStartTime().getYear()),c);
		
		c.gridy++;
		notificationPanel.add(new JLabel("Tid:\t" + "Fra: " + appointment.getStartTime().getHours()+":"+appointment.getStartTime().getMinutes() + "\t Til " + appointment.getEndTime().getHours() + ":" + appointment.getEndTime().getMinutes()),c);
		
		c.gridy++;
		seAvtaleButton = new JButton("Se avtale");
		seAvtaleButton.setPreferredSize(new Dimension(300, 30));
		seAvtaleButton.addActionListener(actionListener); seAvtaleButton.setName("seAvtaleButton");
		notificationPanel.add(seAvtaleButton,c);
		
		add(notificationPanel);
		
		setLocationRelativeTo(null);
		setLocation(this.getX()-(this.getWidth()/2), this.getY());
		setAlwaysOnTop(true);
		pack();
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
	}
	
	private ActionListener actionListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == seAvtaleButton){
				//TODO: skriv noe kode her for å vise avtale.
				thisFrame.removeAll();
				thisFrame.dispose();
			}
			
		}
	};
}
