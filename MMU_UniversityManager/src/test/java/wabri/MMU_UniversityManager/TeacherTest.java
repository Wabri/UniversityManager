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
		String name = "name";
		teacher = new Teacher(name);
		
		assertEquals(name, teacher.getName());
	}
	
}
