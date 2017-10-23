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
	public void testNewTeacherHaveAName() {
		String name = "Name";
		teacher = createNewTestTeacher(name, "", "");

		assertEquals(name, teacher.getName());
	}

	@Test
	public void testNewTeacherHaveASurname() {
		String surname = "Surname";
		teacher = createNewTestTeacher("", surname, "");
		
		assertEquals(surname, teacher.getSurname());
	}

	@Test
	public void testNewTeacherHaveAId() {
		String id = "ID";
		teacher = createNewTestTeacher("","",id);
		
		assertEquals(id, teacher.getId());
	}
	
	@Test
	public void testAskMailWhenMailIsNull() {
		teacher.askMail();
		
		assertEquals("Mail", teacher.getMail());
	}

	private Teacher createNewTestTeacher(String name, String surname, String id) {
		return new Teacher(name, surname,id,mailService);
	}
}
