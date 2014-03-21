package calculations;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.Timer;

import mail.GMail;
import objects.Appointment;
import visual.AlarmView;
import visual.MainFrame;

public class Alarm{

	public Alarm(int minutesBefore, final Appointment appointment) {
		System.out.println("alarmCreated");
		int millisBefore = minutesBefore*60000;
		appointment.getStartTime().setYear(appointment.getStartTime().getYear()-1900);
		appointment.getStartTime().setMonth(appointment.getStartTime().getMonth()-1);
		long tidTilAvtaleAlarm = ((appointment.getStartTime().getTime())-(System.currentTimeMillis())-millisBefore);
		try{
			int tidTilAvtaleAlarmInt = (int) tidTilAvtaleAlarm;
			Timer timer = new Timer(tidTilAvtaleAlarmInt, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					showAlarmView(appointment);
					System.out.println("alarm Triggered");
				}
			});
			timer.setRepeats(false);
			timer.start();
		}catch (Exception x){
		}

	}

	protected void showAlarmView(Appointment appointment) {
		//GMail alarmMail = new GMail();

		System.out.println("appYear: " + appointment.getStartTime().getYear());
		appointment.getStartTime().setYear(appointment.getStartTime().getYear()-1900);
		appointment.getStartTime().setMonth(appointment.getStartTime().getMonth()-1);
		long tidTilAvtale = ((appointment.getStartTime().getTime()/60000)-(System.currentTimeMillis()/60000));
		//String mailTekst = "Din avtale " + appointment.getName() + " starter om " + tidTilAvtale + " minutter. Mvh, CalTwenty";
		//alarmMail.sendMail(MainFrame.getCurrentUser().getEmail(), "Alarm om avtalen" + appointment.getName(),mailTekst );
		AlarmView alarmView = new AlarmView(appointment);
	}




}