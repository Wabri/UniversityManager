package wabri.MMU_UniversityManager;

import java.util.List;

public interface UniversityDB {

	void studentRequestTutor(Student student, String idTeacher);

	void studentRemoveTutor(Student student);

	List<CourseRequest> getRequestedCoursesOfStudent(String idStudent);

}
