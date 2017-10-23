package wabri.MMU_UniversityManager;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class StudentTest {
	
	Student student;

	@Before
	public void init() {
		student = new Student("Name");
	}
	
	@Test
	public void testNewStudentHaveAName() {
		String name = "Test Name";
		student = new Student(name);
		
		assertEquals(name, student.getName());
	}

}
