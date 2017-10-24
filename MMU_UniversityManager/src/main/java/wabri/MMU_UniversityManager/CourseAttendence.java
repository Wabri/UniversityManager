package wabri.MMU_UniversityManager;

public class CourseAttendence {

	private Student student;
	private Teacher teacher;

	public CourseAttendence(Student student, Teacher teacher) {
		this.setStudent(student);
		this.setTeacher(teacher);
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getIdStudent() {
		return this.student.getId();
	}

}
