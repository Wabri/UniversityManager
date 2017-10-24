package wabri.MMU_UniversityManager;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class CourseTest {

	private Course course;
	private Teacher teacher;
	
	@Before
	public void init() {
		teacher = mock(Teacher.class);
		course = createNewTestCourse("ID","name",teacher);
	}

	@Test
	public void testNewCourseHaveAId() {
		String id = "IDTest";
		course = createNewTestCourse(id);
		
		assertEquals(id, course.getId());
	}
	
	@Test
	public void testNewCourseHaveAName() {
		String name = "nameTest";
		course = createNewTestCourse("",name);
		
		assertEquals(name, course.getName());
	}

	@Test
	public void testMailOfCourseIsTheTeacherMail() {
		String mail = "MailTest";
		when(teacher.getMail()).thenReturn(mail);
		
		assertEquals(mail, course.getMail());
	}
	
	@Test
	public void testCourseHaveCanGetIdTeacher() {
		String id = "idTeacherTest";
		when(teacher.getId()).thenReturn(id);
		
		assertEquals(id, course.getIdTeacher());
	}
	
	private Course createNewTestCourse(String id, String name) {
		return new Course(id,name, null);
	}

	private Course createNewTestCourse(String id) {
		return new Course(id, "", null);
	}
	
	private Course createNewTestCourse(String id, String name, Teacher teacher) {
		return new Course(id, name, teacher);
	}
	
}
