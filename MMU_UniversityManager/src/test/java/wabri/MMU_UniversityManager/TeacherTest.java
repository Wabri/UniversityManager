package wabri.MMU_UniversityManager;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class TeacherTest {

	Teacher teacher;
	private MailService mailService;

	@Before
	public void init() {
		mailService = mock(MailService.class);
		teacher = createNewTestTeacher("NameTest", "SurnameTest", "ID0");

		when(mailService.getMail(teacher)).thenReturn("Mail");
	}

	@Test
	public void testNewTeacherHasData() {
		String name = "Name";
		String surname = "Surname";
		String id = "ID";
		teacher = createNewTestTeacher(name, surname, id);

		assertEquals(name, teacher.getName());
		assertEquals(surname, teacher.getSurname());
		assertEquals(id, teacher.getId());
	}

	@Test
	public void testAskMailWhenMailIsNull() {
		assertMailTeacher("Mail");
	}

	@Test
	public void testAskMailWhenMailIsNotNull() {
		String mail = "MailTest";
		teacher.setMail(mail);

		assertMailTeacher(mail);
	}

	private void assertMailTeacher(String expected) {
		teacher.askMail();

		assertEquals(expected, teacher.getMail());
	}

	private Teacher createNewTestTeacher(String name, String surname, String id) {
		return new Teacher(name, surname, id, mailService);
	}
}
