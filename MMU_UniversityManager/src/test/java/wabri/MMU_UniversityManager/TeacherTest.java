package wabri.MMU_UniversityManager;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class TeacherTest {

	Teacher teacher;
	private MailService mailService;
	private Course course;

	@Before
	public void init() {
		course = createNewCourse();
		mailService = mock(MailService.class);
		teacher = createNewTestTeacher("NameTest", "SurnameTest", "ID0");

		when(mailService.getMail(teacher)).thenReturn("Mail");
	}

	@Test
	public void testNewTeacherHasData() {
		String name = "Name";
		String surname = "Surname";
		String id = "ID";
		teacher = createNewTestTeacher(name, surname, id);

		assertEquals(name, teacher.getName());
		assertEquals(surname, teacher.getSurname());
		assertEquals(id, teacher.getId());
	}

	@Test
	public void testAskMailWhenMailIsNull() {
		assertMailTeacher("Mail");
	}

	@Test
	public void testAskMailWhenMailIsNotNull() {
		String mail = "MailTest";
		teacher.setMail(mail);

		assertMailTeacher(mail);
	}

	@Test
	public void testNewTeacherHasNoTeachCourse() {
		assertEquals(0, teacher.getListCoursesTeach().size());
	}

	@Test
	public void testAddTeachCourse() {
		teacher.addCourseTeach(course);

		assertEquals(1, teacher.getListCoursesTeach().size());
		assertEquals(course.getId(), teacher.getListCoursesTeach().get(0).getId());
	}

	@Test
	public void testRemoveTeachCourse() {
		teacher.addCourseTeach(course);
		teacher.removeCourseTeach(course.getId());
		
		assertEquals(0, teacher.getListCoursesTeach().size());
	}
	
	@Test (expected = NoTeachCoursesError.class)
	public void testRemoveTeachCourseWhenListIsEmpty() {
		teacher.removeCourseTeach("idCourse");
		
		assertEquals(0, teacher.getListCoursesTeach().size());
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void testRemoveTeachCourseWhenIdOfCourseIsNotRight() {
		teacher.addCourseTeach(course);
		teacher.removeCourseTeach("idError");
		
		assertEquals(1, teacher.getListCoursesTeach().size());
	}
	
	private Course createNewCourse() {
		return new Course("idCourseTest", "nameCourseTest", teacher);
	}

	private void assertMailTeacher(String expected) {
		teacher.requestMail();

		assertEquals(expected, teacher.getMail());
	}

	private Teacher createNewTestTeacher(String name, String surname, String id) {
		return new Teacher(name, surname, id, mailService);
	}
}
