package wabri.MMU_UniversityManager;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class StudentTest {
	
	private Student student;
	
	
	@Before
	public void init () {
		student = new Student("Test Name","Test Surname");
	}
	
	@Test
	public void testCreationOfMailWhenNewStudentIsInvoked () {
		String nameStudent = "Name";
		String surnameStudent = "Surname";
		student = new Student(nameStudent, surnameStudent);
				
		assertEquals(nameStudent+"."+surnameStudent+"@uniMail.com", student.getMail());
	}
	
}
