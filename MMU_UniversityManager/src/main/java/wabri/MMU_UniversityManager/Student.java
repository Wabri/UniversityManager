package wabri.MMU_UniversityManager;

import java.util.ArrayList;
import java.util.List;

public class Student {

	private String name;
	private String surname;
	private String id;
	private String mail;
	private String idTutor;
	private List<Course> enrolledCourse;
	private MailService mailService;

	public Student(String name, String surname, String id, MailService mailService) {
		this.setName(name);
		this.setSurname(surname);
		this.setId(id);
		this.mailService = mailService;
		enrolledCourse = new ArrayList<Course>();
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

	public String getIdTutor() {
		return idTutor;
	}

	public List<Course> getEnrolledCourse() {
		return enrolledCourse;
	}

	public void addEnrolledCourse(Course course) {
		enrolledCourse.add(course);
	}

	public void removeEnrolledCourse(String idCourse) throws Error{
		if (enrolledCourse.isEmpty()) {
			throw new Error();
		} else {
			int index = 0;
			while(enrolledCourse.get(index) != null) {
				if (idCourse == enrolledCourse.get(index).getId()) {
					enrolledCourse.remove(index);
					return;
				}
				index++;
			}
		}
	}

}