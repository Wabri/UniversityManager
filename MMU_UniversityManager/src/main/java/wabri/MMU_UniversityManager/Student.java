package wabri.MMU_UniversityManager;

public class Student {

	private String name;
	private String surname;
	private String id;

	public Student(String name, String surname, String id) {
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
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
