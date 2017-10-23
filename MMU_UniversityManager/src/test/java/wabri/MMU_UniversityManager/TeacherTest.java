package wabri.MMU_UniversityManager;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TeacherTest {
	
	Teacher teacher;

	@Before
	public void init() {
		
	}
	
	@Test
	public void testNewTeacherHaveAName() {
		String name = "Name";
		teacher = createNewTestTeacher(name,"");
		
		assertEquals(name, teacher.getName());
	}
	
	@Test
	public void testNewTeacherHaveASurname() {
		String surname = "Surname";
		teacher = createNewTestTeacher("", surname);
	}

	private Teacher createNewTestTeacher(String name, String surname) {
		return new Teacher(name,surname);
	}
}
