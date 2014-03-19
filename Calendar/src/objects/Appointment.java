package objects;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Date;

public class Appointment {
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	private String name;
	private Date startTime;
	private Date endTime;
	private String description;
	private Person appointmentOwner;
	private ArrayList<Person> participants = new ArrayList<Person>();

	// the location of the meeting is either chosen from a list of MeetinRroom objects or entered as a string
	private String place;
	private MeetingRoom meetingRoom;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		String oldName = this.name;
		this.name = name;
		System.out.println(name);
		pcs.firePropertyChange("name", oldName, name);
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		Date oldStartTime = this.startTime;
		this.startTime = startTime;
		System.out.println(startTime.toString());
		pcs.firePropertyChange("startTime", oldStartTime, startTime);
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		Date oldEndTime = this.endTime;
		this.endTime = endTime;
		System.out.println(endTime.toString());
		pcs.firePropertyChange("endTime", oldEndTime, endTime);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		String oldDescription = this.description;
		this.description = description;
		System.out.println(description);
		pcs.firePropertyChange("description", oldDescription, description);
	}

	public void addParticipant(Person participant) {
		ArrayList<Person> oldParticipants = new ArrayList<Person>();
		for (int i = 0; i < participants.size(); i++) {
			oldParticipants.add(participants.get(i));
		}
		participants.add(participant);
		System.out.println(participants.size());
		pcs.firePropertyChange("person added", oldParticipants, participants);
	}

	public void removeParticipant(Person participant) {
		ArrayList<Person> oldParticipants = new ArrayList<Person>();
		for (int i = 0; i < participants.size(); i++) {
			oldParticipants.add(participants.get(i));
		}
		participants.remove(participant);
		System.out.println(participants.size());
		pcs.firePropertyChange("person removed", oldParticipants, participants);
	}

	public void removeParticipant(int i) {
		removeParticipant(participants.get(i));
	}

	public Person getParticipant(int i) {
		if (i < participants.size()) {
			return participants.get(i);
		}
		else {
			System.out.println("There aren't that many participants");
			return null;
		}
	}

	public ArrayList<Person> getParticipants() {
		return participants;
	}

	public int numberOfParticipants() {
		return participants.size();
	}

	public boolean isParticipating(Person person) {
		for (int i = 0; i < participants.size(); i++) {
			if (person.equals(participants.get(i))) {
				return true;
			}
		}

		return false;
	}
	
	

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public void setAppointmentOwner(Person appointmentOwner) {
		this.appointmentOwner = appointmentOwner;
	}

	public MeetingRoom getMeetingRoom() {
		return meetingRoom;
	}

	public void setMeetingRoom(MeetingRoom meetingRoom) {
		this.meetingRoom = meetingRoom;
	}

	public Person getAppointmentOwner() {
		return appointmentOwner;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
}