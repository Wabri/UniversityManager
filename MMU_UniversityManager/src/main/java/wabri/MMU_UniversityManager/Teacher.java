package wabri.MMU_UniversityManager;

public class Teacher {

	private String name;
	private String surname;
	private String id;
	private String mail;
	private MailService mailService;

	public Teacher(String name, String surname, String id, MailService mailService) {
		this.setName(name);
		this.setSurname(surname);
		this.setId(id);
		this.mailService = mailService;
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

	public void askMail() {
		if (mail==null) {
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
