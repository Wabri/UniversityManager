package wabri.MMU_UniversityManager;

import java.util.ArrayList;
import java.util.List;

public class Course {

	private String id;
	private String name;
	private Teacher teacher;
	private List<Student> enrolledStudents;

	public Course(String id, String name, Teacher teacher) {
		this.setId(id);
		this.setName(name);
		this.setTeacher(teacher);
		enrolledStudents = new ArrayList<Student>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public String getMail() {
		return teacher.getMail();
	}

	public String getIdTeacher() {
		return teacher.getId();
	}

	public List<Student> getEnrolledStudent() {
		return enrolledStudents;
	}

	public void addEnrolledStudent(Student studentEnrolled) throws CourseAttendenceAlreadyActive{
		if (enrolledStudents.contains(studentEnrolled)) {
			throw new CourseAttendenceAlreadyActive();
		}
		enrolledStudents.add(studentEnrolled);
	}

	public void removeEnrolledStudent(String idStudentToRemove) {
		enrolledStudents.remove(0);		
	}

}
