package wabri.MMU_UniversityManager;

public interface UniversityDB {

	void studentRequestTutor(Student student, String idTeacher);

	void studentRemoveTutor(Student student);

	void studentRequestCourse(Student student, String idCourse);

	void studentRemoveCourse(Student student, String id);

	Teacher findTeacherWithId(String string);

	void createTutoring(TutorRequest tutorRequest);

	void createTutorRequest(Teacher teacher, Student student);

	Course findCourseWithId(String idCourse);

	void createCourseAttendence(Course course, String idStudent);

}
