package wabri.MMU_UniversityManager;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class StudentTest {

	Student student;

	@Before
	public void init() {
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

	private Student createNewTestStudent(String name, String surname, String id) {
		return new Student(name, surname, id);
	}

}
