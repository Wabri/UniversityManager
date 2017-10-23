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
		student = new Student(name, "Surname");

		assertEquals(name, student.getName());
	}

	@Test
	public void testNewStudentHaveASurname() {
		String surname = "Test Surname";
		student = new Student("Name", surname);
		
		assertEquals(surname, student.getSurname());
	}

}
