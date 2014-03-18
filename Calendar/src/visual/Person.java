package visual;

import java.util.ArrayList;

public class Person implements Searchable {

	public String name;
	public int tlf;
	public String epost;
	public ArrayList<Person> andreKalendere;
	//TODO legg til en kalender
	
	public Person(String navn, String epost, int tlf) {
		this.name = navn;
		this.epost = epost;
		this.tlf = tlf;
		andreKalendere = new ArrayList<Person>();
		//TODO fiks personens kalender
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}