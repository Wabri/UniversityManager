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
	private UniversityDB universityDB;

	public Student(String name, String surname, String id, MailService mailService, UniversityDB universityDB) {
		this.setName(name);
		this.setSurname(surname);
		this.setId(id);
		this.mailService = mailService;
		this.universityDB = universityDB;
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

	public void setIdTutor(String idTutor) {
		this.idTutor = idTutor;
	}

	public List<Course> getEnrolledCourse() {
		return enrolledCourse;
	}

	public void addEnrolledCourse(Course course) {
		enrolledCourse.add(course);
	}

	public void removeEnrolledCourse(String idCourse) throws Error {
		if (enrolledCourse.isEmpty()) {
			throw new NoEnrolledCourseError();
		} else {
			int index = 0;
			while (enrolledCourse.get(index) != null) {
				if (idCourse == enrolledCourse.get(index).getId()) {
					enrolledCourse.remove(index);
					return;
				}
				index++;
			}
		}
	}

	public void sendTutorRequest(String idTeacher) throws IllegalTutorRequest {
		if (this.getIdTutor() != null) {
			throw new IllegalTutorRequest();
		}
		universityDB.studentAskTutor(this, idTeacher);			
	}

	public void sendTutorRemoveRequest() throws NoTutorAssignedError {
		if (this.getIdTutor() == null) {
			throw new NoTutorAssignedError();
		}
		universityDB.studentRemoveTutor(this);
	}

}