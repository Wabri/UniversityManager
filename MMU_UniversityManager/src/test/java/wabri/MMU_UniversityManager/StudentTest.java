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
		student = createNewTestStudent(name, "Surname");

		assertEquals(name, student.getName());
	}

	@Test
	public void testNewStudentHaveASurname() {
		String surname = "Test Surname";
		student = createNewTestStudent("Name", surname);
		
		assertEquals(surname, student.getSurname());
	}
	
	private Student createNewTestStudent(String name, String surname) {
		return new Student(name, surname);
	}

}
