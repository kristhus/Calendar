package objects;

import java.util.ArrayList;

public class Person extends Participant {
	private int tlf;
	private ArrayList<Participant> otherCalenders;
	
	public Person(String name, String email) {
		setName(name);
		setEmail(email);
	}
	
	public Person(String name, String email, int tlf) {
		this(name, email);
		this.tlf = tlf;
	}
	
	public int getTlf() {
		return tlf;
	}
	
	public void setTlf(int tlf) {
		this.tlf = tlf;
	}
}