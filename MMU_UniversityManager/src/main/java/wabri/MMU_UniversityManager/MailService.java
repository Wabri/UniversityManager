package wabri.MMU_UniversityManager;

public interface MailService {

	String getMail(Student student);

	String getMail(Teacher teacher);

	void sendMail(Teacher teacher, Student student, String mail);

}
