package wabri.MMU_UniversityManager;

import java.util.ArrayList;
import java.util.List;

public class Course {

	private String id;
	private String name;
	private Teacher teacher;
	private List<Student> enrolledStudents;
	private List<CourseRequest> studentsCourseRequest;
	private List<CourseAttendence> coursesAttendence;

	public Course(String id, String name, Teacher teacher) {
		this.setId(id);
		this.setName(name);
		this.setTeacher(teacher);
		enrolledStudents = new ArrayList<Student>();
		studentsCourseRequest = new ArrayList<CourseRequest>();
		coursesAttendence = new ArrayList<CourseAttendence>();
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

	public void addEnrolledStudent(Student studentEnrolled) throws CourseAttendenceAlreadyActive {
		if (enrolledStudents.contains(studentEnrolled)) {
			throw new CourseAttendenceAlreadyActive();
		}
		enrolledStudents.add(studentEnrolled);
	}

	public void removeEnrolledStudent(String idStudentToRemove) {
		if (enrolledStudents.isEmpty()) {
			throw new NoEnrolledStudentError();
		} else {
			try {
				int index = 0;
				while (enrolledStudents.get(index) != null) {
					if (enrolledStudents.get(index).getId() == idStudentToRemove) {
						enrolledStudents.remove(index);
						break;
					}
					index++;
				}
			} catch (IndexOutOfBoundsException e) {
				throw new NoEnrolledStudentWithThisId();
			}
		}
	}

	public void addCourseRequest(CourseRequest courseRequest) {
		studentsCourseRequest.add(courseRequest);
	}

	public List<CourseRequest> getStudentsCourseRequest() {
		return studentsCourseRequest;
	}

	public void removeCourseRequestFromStudent(String idStudentCourseToRemove)
			throws NoStudentCourseRequestError, NoCourseRequestActiveForThisStudent {
		if (studentsCourseRequest.isEmpty()) {
			throw new NoStudentCourseRequestError();
		} else {
			try {
				int index = 0;
				while (studentsCourseRequest.get(index) != null) {
					if (studentsCourseRequest.get(index).getIdStudent() == idStudentCourseToRemove) {
						studentsCourseRequest.remove(index);
						break;
					}
					index++;
				}
			} catch (IndexOutOfBoundsException e) {
				throw new NoCourseRequestActiveForThisStudent();
			}
		}
	}

	public void addCourseAttendence(CourseAttendence courseAttendence) {
		coursesAttendence.add(courseAttendence);
	}

	public List<CourseAttendence> getCoursesAttendence() {
		return coursesAttendence;
	}

	public void removeCourseAttendence(String idStudentToRemove) throws NoCourseAttendenceError {
		if (coursesAttendence.isEmpty()) {
			throw new NoCourseAttendenceError();
		} else {
			try {
				int index = 0;
				while (coursesAttendence.get(index) != null) {
					if (coursesAttendence.get(index).getIdStudent() == idStudentToRemove) {
						coursesAttendence.remove(index);
						break;
					}
					index++;
				}
			} catch (IndexOutOfBoundsException e) {
				throw new NoCourseAttendenceError();
			}

		}
	}

}
