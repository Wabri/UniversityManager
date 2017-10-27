package wabri.MMU_UniversityManager;

import java.util.ArrayList;
import java.util.List;

public class Teacher {

	private String name;
	private String surname;
	private String id;
	private String mail;
	private MailService mailService;
	private List<Course> coursesTeach;
	private List<Student> tutoredStudents;
	private List<TutorRequest> requestedTutor;

	public List<TutorRequest> getRequestedTutor() {
		return requestedTutor;
	}

	public Teacher(String name, String surname, String id, MailService mailService) {
		this.setName(name);
		this.setSurname(surname);
		this.setId(id);
		this.mailService = mailService;
		coursesTeach = new ArrayList<Course>();
		tutoredStudents = new ArrayList<Student>(3);
		requestedTutor = new ArrayList<TutorRequest>();
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

	public void requestMail() {
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

	public List<Course> getListCoursesTeach() {
		return coursesTeach;
	}

	public void addCourseTeach(Course course) {
		coursesTeach.add(course);
	}

	public void removeCourseTeach(String idCourse) {
		if (coursesTeach.isEmpty()) {
			throw new NoTeachCoursesError();
		} else {
			int index = 0;
			while (coursesTeach.get(index) != null) {
				if (coursesTeach.get(index).getId() == idCourse) {
					coursesTeach.remove(index);
					return;
				}
				index++;
			}
		}
	}

	public List<Student> getTutoredStudents() {
		return tutoredStudents;
	}

	public void addTutoredStudent(Student student) throws OutOfLimitTutoredStudents {
		if (tutoredStudents.size() < 3) {
			tutoredStudents.add(student);
		} else {
			throw new OutOfLimitTutoredStudents();
		}
	}

	public void addRequestedTutoring(TutorRequest tutorRequest) throws OutOfLimitTutoredStudents {
		if (tutoredStudents.size() < 3) {
			requestedTutor.add(tutorRequest);
		} else {
			throw new OutOfLimitTutoredStudents();
		}
	}

	public List<TutorRequest> getTutorRequest() {
		return null;
	}

	public void acceptTutorRequest(String idStudent) throws NoTutorRequestError, OutOfLimitTutoredStudents {
		if (tutoredStudents.size() >= 3) {
			throw new OutOfLimitTutoredStudents();
		} else {
			for (TutorRequest tutorRequest : requestedTutor) {
				if (tutorRequest.getIdStudent() == idStudent) {
					this.addTutoredStudent(tutorRequest.getStudent());
					return;
				}
			}
		}
		throw new NoTutorRequestError();
	}

}
