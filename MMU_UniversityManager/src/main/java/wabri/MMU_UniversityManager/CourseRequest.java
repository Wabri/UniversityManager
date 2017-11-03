package wabri.MMU_UniversityManager;

public class CourseRequest {

	private Student student;
	private Course course;

	public CourseRequest(Student student, Course course) {
		this.setStudent(student);
		this.setCourse(course);
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
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

	public String getIdCourse() {
		return this.course.getId();
	}

}
