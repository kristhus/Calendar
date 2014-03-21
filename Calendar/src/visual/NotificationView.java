package visual;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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

public class NotificationView extends JFrame{

	private JButton seAvtaleButton;
	private JFrame thisFrame;
	private JButton godtaButton;
	private JButton avslaButton;
	private MainFrame mainFrame;
	private Appointment appointment;

	public NotificationView (MainFrame mainFrame, Appointment appointment){
		super("Du er invitert til en ny avtale");
		thisFrame = this;
		this.appointment = appointment;
		this.mainFrame = mainFrame;
		JPanel notificationPanel = new JPanel();
		notificationPanel.setPreferredSize(new Dimension(500, 150));
		notificationPanel.setBackground(Color.white);
		notificationPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0; c.gridy=0;
		c.ipadx = 75; c.ipady = 3;
		Font fatFont = new Font(Font.SANS_SERIF, Font.BOLD, new JLabel().getFont().getSize());
		c.fill = GridBagConstraints.HORIZONTAL;

		notificationPanel.add(new JLabel("Avtaleoppretter:"),c); 
		JLabel appointmentOwnerLabel = new JLabel(appointment.getAppointmentOwner().getName()); appointmentOwnerLabel.setFont(fatFont); c.gridx++;
		notificationPanel.add(appointmentOwnerLabel,c); c.gridx--; c.gridy++;
		
		notificationPanel.add(new JLabel("AvtaleNavn:"),c);
		JLabel appointmentNameLabel = new JLabel(appointment.getName()); appointmentNameLabel.setFont(fatFont); c.gridx++;
		notificationPanel.add(appointmentNameLabel,c);c.gridx--;c.gridy++;
		
		notificationPanel.add(new JLabel("Dato:"),c);
		JLabel datoLabel = new JLabel(appointment.getStartTime().getDate() + "." + appointment.getStartTime().getMonth()+"."+appointment.getStartTime().getYear()); datoLabel.setFont(fatFont); c.gridx++;
		notificationPanel.add(datoLabel,c);c.gridx--;c.gridy++;
		
		notificationPanel.add(new JLabel("Tid:"),c);
		JLabel klokkeLabel = new JLabel(appointment.getStartTime().getHours()+":"+appointment.getStartTime().getMinutes() + " - " + appointment.getEndTime().getHours() + ":" + appointment.getEndTime().getMinutes()); klokkeLabel.setFont(fatFont); c.gridx++;
		notificationPanel.add(klokkeLabel,c);c.gridx--;c.gridy++;
		
		c.gridy++;
		
		c.gridwidth = 2;
		seAvtaleButton = new JButton("Se avtale");
		seAvtaleButton.addActionListener(actionListener); seAvtaleButton.setName("seAvtaleButton");
		notificationPanel.add(seAvtaleButton,c);
		c.gridwidth = 1;
		c.gridy++;
		
		avslaButton = new JButton("Avslå");
		avslaButton.addActionListener(actionListener); avslaButton.setName("avslaButton");
		notificationPanel.add(avslaButton,c);
		c.gridx++;
		
		godtaButton = new JButton("Godta");
		godtaButton.addActionListener(actionListener); godtaButton.setName("godtaButton");
		notificationPanel.add(godtaButton,c);
		
		add(notificationPanel);
		
		setLocationRelativeTo(null);
		setLocation(this.getX()-(this.getWidth()/2), this.getY());
		setAlwaysOnTop(true);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
	}
	
	private ActionListener actionListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == seAvtaleButton){
				AppointmentView appointmentView = new AppointmentView(MainFrame.getCurrentUser(), appointment);
				JFrame appointmentFrame = new JFrame("CalTwenty - Avtalevisning");
				appointmentFrame.setPreferredSize(new Dimension(800,600));
				appointmentFrame.add(appointmentView);
				appointmentFrame.pack();
				appointmentFrame.setLocationRelativeTo(null);
				appointmentFrame.setAlwaysOnTop(true);
				appointmentFrame.setVisible(true);
				appointmentFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				//nåverende bruker , appointment
				
				//TODO: skriv noe kode her for å vise avtale.
				thisFrame.removeAll();
				thisFrame.dispose();
			}
			
		}
	};
}
