package wabri.MMU_UniversityManager;

public class Student {

	private String name;
	private String surname;
	private String eMail;

	public Student(String name, String surname) {
		this.name = name;
		this.surname = surname;
		this.eMail = this.name+"."+this.surname+"@uniMail.com";
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getMail() {
		return eMail;
	}

}
