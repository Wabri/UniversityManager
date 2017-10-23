package wabri.MMU_UniversityManager;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class StudentTest {

	private Student student;
	private MailService mailService;

	@Before
	public void init() {
		mailService = mock(MailService.class);
		student = createNewTestStudent("Name", "Surname", "ID");
		
		when(mailService.getMail(student)).thenReturn("Mail");
	}

	@Test
	public void testNewStudentHaveAName() {
		String name = "Test Name";
		student = createNewTestStudent(name, "", "");

		assertEquals(name, student.getName());
	}

	@Test
	public void testNewStudentHaveASurname() {
		String surname = "Test Surname";
		student = createNewTestStudent("", surname, "");

		assertEquals(surname, student.getSurname());
	}

	@Test
	public void testNewStudentHaveAnId() {
		String id = "IdTest";
		student = createNewTestStudent("", "", id);

		assertEquals(id, student.getId());
	}

	@Test
	public void testNewStudentHaveAMail() {
		student.askMail();
		
		assertTrue(student.getMail()=="Mail");
	}

	private Student createNewTestStudent(String name, String surname, String id) {
		return new Student(name, surname, id, mailService);
	}

}
