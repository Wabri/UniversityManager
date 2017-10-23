package wabri.MMU_UniversityManager;

public class Student {

	private String name;
	private String surname;
	private String id;
	private MailService mailService;
	private String mail;

	public Student(String name, String surname, String id, MailService mailService) {
		this.mailService = mailService;
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

	public void askMail() {
		if (mail == null) {
			setMail(mailService.getMail(this));			
		}
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

}