package wabri.MMU_UniversityManager;

import java.util.ArrayList;
import java.util.List;

public class Student {

	private String name;
	private String surname;
	private String eMail;
	private List<Course> listOfSubscribedCourse;

	public Student(String name, String surname) {
		this.name = name;
		this.surname = surname;
		this.eMail = this.name + "." + this.surname + "@uniMail.com";
		listOfSubscribedCourse = new ArrayList<Course>();
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

}
