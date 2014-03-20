package objects;

import java.util.ArrayList;

public class Group extends Participant {
	private ArrayList<Person> persons = new ArrayList<Person>();
	
	public Group(String name, String email) {
		setName(name);
		setEmail(email);
	}
	
	public ArrayList<Person> getPersons() {
		return persons;
	}
	
	public void setPersons(ArrayList<Person> persons) {
		this.persons = persons;
	}
	
	public void addPerson(Person person) {
		persons.add(person);
	}
	
	public void removePerson(Person person) {
		persons.remove(person);
	}
	
	public boolean contains(Person person) {
		return persons.contains(person);
	}
	
	public int size() {
		return persons.size();
	}
}