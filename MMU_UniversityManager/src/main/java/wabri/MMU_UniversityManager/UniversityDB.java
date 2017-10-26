package wabri.MMU_UniversityManager;

public interface UniversityDB {

	void studentRequestTutor(Student student, String idTeacher);

	void studentRemoveTutor(Student student);

}
