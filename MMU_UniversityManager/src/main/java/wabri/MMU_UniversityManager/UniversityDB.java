package wabri.MMU_UniversityManager;

public interface UniversityDB {

	void studentRequestTutor(Student student, String idTeacher);

	void studentRemoveTutor(Student student);

	void studentRequestCourse(Student student, String idCourse);

	void studentRemoveCourse(Student student, String id);

}
