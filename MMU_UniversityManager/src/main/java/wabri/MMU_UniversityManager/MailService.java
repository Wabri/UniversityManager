package wabri.MMU_UniversityManager;

public interface MailService {

	String getMail(Student student);

	String getMail(Teacher teacher);

	void sendMail(Teacher teacher, Student student, String mail);
	
	void sendMail(Student student, Teacher teacher, String message);

	void sendMail(Course course, Teacher teacher, String message);

	void sendMail(Course course, Student student, String message);

}
