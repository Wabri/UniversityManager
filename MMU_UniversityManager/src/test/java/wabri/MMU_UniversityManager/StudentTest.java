package wabri.MMU_UniversityManager;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations.Mock;

public class StudentTest {

	private Student student;
	private UniversityDB universityDB;

	@Before
	public void init() {
		student = new Student("ID0", "Test Name", "Test Surname");
		universityDB = mock(UniversityDB.class);
		
	}

	@Test
	public void testCreationOfMailWhenNewStudentIsInvoked() {
		String nameStudent = "Name";
		String surnameStudent = "Surname";
		student = new Student("ID0", nameStudent, surnameStudent);

		assertEquals(nameStudent + "." + surnameStudent + "@uniMail.com", student.getMail());
	}

	@Test
	public void testNewStudentIsNotSubscribeToAnyCourse() {
		assertEquals(0, student.getListOfSubscribedCourse().size());
	}

	@Test
	public void testSendTutorRequestToTeacher() {
		String id = "ID0";
		Teacher teacher = new Teacher(id, "name Test Teacher", "surname Test Teacher");

		student.sendTutorRequestToTeacher(id);

		assertEquals(id, student.getTutor().getId());
	}

}
