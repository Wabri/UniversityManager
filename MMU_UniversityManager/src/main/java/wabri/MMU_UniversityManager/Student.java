package wabri.MMU_UniversityManager;

import java.util.ArrayList;
import java.util.List;

public class Student {

	private String name;
	private String surname;
	private String eMail;
	private List<Course> listOfSubscribedCourse;
	private Teacher tutor;
	private String id;
	private UniversityDB universityDB;

	public Student(String id, String name, String surname) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.eMail = this.name + "." + this.surname + "@uniMail.com";
		listOfSubscribedCourse = new ArrayList<Course>();
	}
	
	public String getId() {
		return id;
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

	public List<Course> getListOfSubscribedCourse() {
		return listOfSubscribedCourse;
	}

	public void sendTutorRequestToTeacher(String idTeacher) {
		universityDB.createTutorRequest(this.id,idTeacher);
	}

	public Teacher getTutor() {
		return tutor;
	}

}
