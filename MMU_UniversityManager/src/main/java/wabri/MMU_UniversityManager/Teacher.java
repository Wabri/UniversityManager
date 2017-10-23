package wabri.MMU_UniversityManager;

public class Teacher {

	private String name;
	private String surname;
	private String id;

	public Teacher(String name, String surname, String id) {
		this.setName(name);
		this.setSurname(surname);
		this.setId(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
