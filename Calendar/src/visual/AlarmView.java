package visual;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class AlarmView extends JPanel{
	JLabel avtaleNavn;
	JLabel arrNavn;
	JLabel avtaleTid;
	JLabel avtaleDato;
	JLabel avtaleSted;
	String navn;
	String dato;
	String tid;
	String sted;

	public AlarmView(){

	JPanel alarmPanel = new JPanel(new GridBagLayout());
	add(alarmPanel);
	
	navn = "Trond";
	dato = "14.04.2014";
	tid = "14:00 - 16:00";
	sted = "Møterommet";
	
	
	avtaleNavn = new JLabel("Varsel");
	arrNavn = new JLabel(navn + " har invitert deg til en avtale");
	avtaleDato = new JLabel("Dato : " + dato);
	avtaleTid = new JLabel("Tid : " + tid);
	avtaleSted = new JLabel("Sted : " + sted);

	GridBagConstraints gbc = new GridBagConstraints();
	gbc.insets = new Insets(5,5,5,5);
	gbc.gridx = 0;
	gbc.gridy = 1;
	alarmPanel.add(avtaleNavn, gbc);
	gbc.gridy = 2;
	alarmPanel.add(arrNavn, gbc);
	gbc.gridy = 3;
	alarmPanel.add(avtaleDato, gbc);
	gbc.gridy = 4;
	alarmPanel.add(avtaleTid, gbc);
	gbc.gridy = 5;
	alarmPanel.add(avtaleSted, gbc);
	}
}
