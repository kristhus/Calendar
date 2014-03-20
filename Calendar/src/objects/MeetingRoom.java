package objects;

import java.sql.Date;

public class MeetingRoom implements Searchable {
	private String name;
	private int capacity;
	// hvert møterom skal ha en kalender som viser når det er booket

	public MeetingRoom(String name, int capacity) {
		this.name = name;
		this.capacity = capacity;
	}
	
	public MeetingRoom(MeetingRoom meetingRoom) {
		if (name != null) {
			this.name = meetingRoom.getName();
		}
		this.capacity = meetingRoom.getCapacity();
	}

	public String getName() {
		return name + " (" + capacity + ")";
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public boolean isAvailabe(Date startTime, Date endTime) {
		// midlertidig
		return true;
	}
}