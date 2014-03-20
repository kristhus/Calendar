package objects;

public abstract class Participant implements Comparable<Participant>, Searchable {
	private String name;
	private String email;

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public int compareTo(Participant other) {
		return name.compareTo(other.getName());
	}
	
	public String toString() {
		return name;
	}
}