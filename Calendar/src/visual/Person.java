package visual;

import java.util.ArrayList;

public class Person {

	public String navn;
	public int tlf;
	public String epost;
	public ArrayList<Person> andreKalendere;
	//TODO legg til en kalender
	
	
	public Person(String navn, String epost, int tlf) {
		this.navn = navn;
		this.epost = epost;
		this.tlf = tlf;
		andreKalendere = new ArrayList<Person>();
		//TODO fiks personens kalender
	}
	
}
