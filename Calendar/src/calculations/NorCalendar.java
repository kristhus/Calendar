package calculations;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import visual.MainFrame;

public class NorCalendar extends GregorianCalendar {
	
	private String[] months = {"Januar", "Februar", "Mars", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Desember"};
	
	
	public int YEAR; // OVERRIDE
	public int MONTH;
	public int DAY_OF_MONTH;
	public int WEEK_OF_MONTH;
	public Date TODAY;
	
	
	public NorCalendar() {
		update();
		TODAY = Calendar.getInstance().getTime();
	}
	
	public void monthBack() {
		add(Calendar.MONTH, -1);
		update();
	}
	
	public void monthForward() {
		add(Calendar.MONTH, 1);
		update();
	}
	public void update() {
		MONTH = get(Calendar.MONTH);	
		YEAR = get(Calendar.YEAR);
		DAY_OF_MONTH = get(Calendar.DAY_OF_MONTH);
		WEEK_OF_MONTH = get(Calendar.WEEK_OF_MONTH);
	}
	
	public int getFirstDayOfMonth() {
		set(Calendar.DAY_OF_MONTH, 1);
		update();
		int toReturn = getTime().getDay()-1;
		if(toReturn < 0) {
			toReturn = 6;
		}
		return toReturn;
	}
	
	public int getLastDayOfMonth() { //INHERITED METHOD WAS BUGGED
		if ((MONTH+1)%2 == 0 && MONTH < 7) {
			if (MONTH == 1) {
				if ( ((YEAR % 4 == 0) && (YEAR % 100 != 0)) || (YEAR % 400 == 0) ) { 
					return 29; 
				}
				else { 
					return 28; 
				}
			}
			return 30;
		}
		if ((MONTH+1)%2 == 1 && MONTH < 7) {
			return 31;
		}
		if ((MONTH+1)%2 == 0 && MONTH >= 7) {
			return 31;
		}
		return 30;
	}
	
	public void setWeek(int week) {
		set(Calendar.WEEK_OF_MONTH, week);
	}
	
	public String month(int i) {
		return months[i];
	}
	
	public void today() {
		Calendar.getInstance().setTime(TODAY);
		update();
	}
	public void setToday() {
		Calendar c = Calendar.getInstance();
		TODAY = c.getTime();
	}
	
	public void lastWeek() {
		add(Calendar.WEEK_OF_MONTH, -1);
		update();
	}
	public void nextWeek() {
		add(Calendar.WEEK_OF_MONTH, 1);
		update();
	}
	
	public String[] getWeekDates() {
		String[] weekDays = {null,null,null,null,null,null,null};
		SimpleDateFormat sdf = new SimpleDateFormat("d.MM.yyyy");
		for(int i = 0; i < 7; i++) {
			set(Calendar.DAY_OF_WEEK, i+2);
			weekDays[i] = sdf.format(getTime());
		}
		return weekDays;
	}
	
	public int getLastDayOfLastWeekInLastMonth() {
		monthBack();
		int lastDay = getLastDayOfMonth();
		monthForward();
		return lastDay;
		
	}
	
	public Date getTheFirstDayOfWeek() {
		Calendar c = this;
		
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
		System.out.println(c.getTime() + " - FØRSTE DAGEN I UKEN");
		return getTime();
	}
	public Date getTheLastDayOfWeek() {
		Calendar c = this;
		c.add(WEEK_OF_YEAR, 1);
		c.add(DAY_OF_YEAR, -1);
		System.out.println(c.getTime() + " - SISTE DAGEN I UKEN");
		Date toReturn = getTime();
		
		return toReturn;
	}
	
	public int getDayOfWeek(Date date) {
		setTime(date);	
//		System.out.println(getTime());
//		System.out.println("DAY OF WEEK: " + get(DAY_OF_WEEK));
		return get(Calendar.DAY_OF_WEEK) -1;
	}
}
