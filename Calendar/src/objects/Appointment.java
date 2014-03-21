package objects;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class Appointment {
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	private Person appointmentOwner;
	private String name;
	private Date startTime;
	private Date endTime;
	private String description;
	private TreeMap<Participant, Boolean> participants = new TreeMap<Participant, Boolean>();

	// the location of the meeting is either chosen from a list of MeetingRoom objects or entered as a string
	private String location;
	private MeetingRoom meetingRoom;
	private int minCapacity; // lagres ikke i databasen, bare midlertidig
	
	public Appointment(Person appointmentOwner) {
		this.appointmentOwner = appointmentOwner;
	}
	
	public Person getAppointmentOwner() {
		return appointmentOwner;
	}

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

	public void addParticipant(Participant participant) {
		TreeMap<Participant, Boolean> oldParticipants = (TreeMap<Participant, Boolean>) this.participants.clone();
		this.participants.put(participant, null);
		System.out.println(participants.size());
		pcs.firePropertyChange("participants", oldParticipants, participants);
	}

	public void removeParticipant(Participant participant) {
		TreeMap<Participant, Boolean> oldParticipants = (TreeMap<Participant, Boolean>) this.participants.clone();
		this.participants.remove(participant);
		System.out.println(participants.size());
		pcs.firePropertyChange("participants", oldParticipants, participants);
	}

	public void removeParticipant(int i) {
		removeParticipant(getParticipant(i));
	}

	public Participant getParticipant(int i) {
		int c = 0;
		for (Map.Entry<Participant, Boolean> entry : participants.entrySet()) {
			if (c == i) {
				return entry.getKey();
			}
			c++;
		}
		return null;
	}

	public TreeMap<Participant, Boolean> getParticipants() {
		return participants;
	}

	public int numberOfParticipants() {
		return participants.size();
	}

	public boolean isParticipating(Participant participant) {
		for (int i = 0; i < participants.size(); i++) {
			if (participant.equals(getParticipant(i))) {
				return true;
			}
		}

		return false;
	}
	
	public Boolean getStatus(Participant participant) {
		return participants.get(participant);
	}
	
	public Boolean getStatus(int i) {
		return participants.get(getParticipant(i));
	}
	
	public void setStatus(Participant participant, Boolean status) {
		Boolean oldStatus = participants.get(participant);
		Object[] oldStatusPair = {participant, oldStatus};
		System.out.println(status);
		participants.put(participant, status);
		Object[] newStatusPair = {participant, status};
		pcs.firePropertyChange("Status changed", oldStatusPair, newStatusPair);
	}
	
	public void setStatus(int i, Boolean status) {
		participants.put(getParticipant(i), status);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		String oldLocation;
		if (location == null) {
			oldLocation = null;
		}
		else {
			oldLocation = this.location;
		}
		this.location = location;
		System.out.println(location);
		pcs.firePropertyChange("location", oldLocation, location);
	}
	
	public MeetingRoom getMeetingRoom() {
		return meetingRoom;
	}
	
	public void setMeetingRoom(MeetingRoom meetingRoom) {
		MeetingRoom oldMeetingRoom;
		if (this.meetingRoom == null) {
			oldMeetingRoom = null;
		}
		else {
			oldMeetingRoom = new MeetingRoom(this.meetingRoom);
		}
		this.meetingRoom = meetingRoom;
		pcs.firePropertyChange("meetingroom", oldMeetingRoom, meetingRoom);
	}
	
	
	// helvettes jævla negerkuk i samefitte
	// return the String "valid" if valid, an error message otherwise
	public String validityStatus() {
		if (name == null || name.length() < 3) {
			return "Avtalenavnet må inneholde minst tre bokstaver";
		}
		else if (endTime.before(startTime)) {
			return "Sluttid kan ikke være før starttid";
		}
		else if (description == null || description.length() < 3) {
			return "Beskrivelsen må inneholde minst tre bokstaver";
		}
		
		return "valid";
	}
	
	public void setMinCapacity(int minCapacity) {
		int oldMinCapacity = this.minCapacity;
		this.minCapacity = minCapacity;
		pcs.firePropertyChange("min capacity", oldMinCapacity, minCapacity);
	}
	
	public int getMinCapacity() {
		return minCapacity;
	}
}