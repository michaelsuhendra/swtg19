package datatypes;

/**
 * Contains the necessary informations about a guest.
 * 
 * @author swe.uni-due.de
 *
 */
public class PersonData {
	private String name;
	private String email;

	public PersonData(String name, String email) {
		this.name = name;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}
	
	public String toString() {
		return name + ", " + email;
	}
}
