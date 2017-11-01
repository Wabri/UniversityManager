package wabri.MMU_UniversityManager;

public interface MailService {

	String getMail(Student student);

	String getMail(Teacher teacher);

	void sendMail(Student student, Teacher teacher, String message);

}
