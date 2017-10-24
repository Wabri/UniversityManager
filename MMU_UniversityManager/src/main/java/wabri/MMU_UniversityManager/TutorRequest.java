package wabri.MMU_UniversityManager;

public class TutorRequest {

	private Teacher teacher;
	private Student student;

	public TutorRequest(Teacher teacher, Student student) {
		this.setTeacher(teacher);
		this.setStudent(student);
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public String getIdStudent() {
		return this.student.getId();
	}

}
