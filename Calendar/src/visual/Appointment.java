package visual;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Date;

public class Appointment {
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	private String name;
	private Date startTime;
	private Date endTime;
	private String description;
	
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
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
}