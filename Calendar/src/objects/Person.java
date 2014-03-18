package objects;

import java.util.ArrayList;

public class Person implements Searchable {

	public String navn;
	public String name;
	public int tlf;
	public String epost;
	public ArrayList<Person> andreKalendere;
	//TODO legg til en kalender
	
	
	public Person(String navn, String epost, int tlf) {
		this.name = navn;
		this.navn = navn;
		this.epost = epost;
		this.tlf = tlf;
		andreKalendere = new ArrayList<Person>();
		//TODO fiks personens kalender
	}


	public String getNavn() {
		return navn;
	}


	public void setNavn(String navn) {
		this.navn = navn;
	}
	
	public String getName() {
		return name;
	}


	public void setName(String navn) {
		this.name = navn;
	}


	public int getTlf() {
		return tlf;
	}


	public void setTlf(int tlf) {
		this.tlf = tlf;
	}


	public String getEpost() {
		return epost;
	}


	public void setEpost(String epost) {
		this.epost = epost;
	}


	public ArrayList<Person> getAndreKalendere() {
		return andreKalendere;
	}


	public void setAndreKalendere(ArrayList<Person> andreKalendere) {
		this.andreKalendere = andreKalendere;
	}
	
	
}
