package wabri.MMU_UniversityManager;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class CourseTest {

	private Course course;
	private Teacher teacher;
	private MailService mailService;

	@Before
	public void init() {
		teacher = mock(Teacher.class);
		mailService = mock(MailService.class);
		course = createNewTestCourse("ID", "name", teacher);
	}

	@Test
	public void testNewCourseHasInformation() {
		String id = "IDTest";
		String name = "nameTest";
		course = createNewTestCourse(id, name, teacher);

		assertEquals(id, course.getId());
		assertEquals(name, course.getName());
		assertEquals(teacher, course.getTeacher());
	}

	@Test
	public void testMailOfCourseIsTheTeacherMail() {
		String mail = "MailTest";
		when(teacher.getMail()).thenReturn(mail);

		assertEquals(mail, course.getMail());
	}

	@Test
	public void testCourseHasGetIdTeacher() {
		String id = "idTeacherTest";
		when(teacher.getId()).thenReturn(id);

		assertEquals(id, course.getIdTeacher());
	}

	@Test
	public void testListOfStudentsEnrollHasNoEnrolledStudents() {
		assertEnrolledStudents(0);
	}

	@Test
	public void testAddEnrolledStudent() {
		course.addEnrolledStudent(createNewTestStudent("idTestStudent"));

		assertEnrolledStudents(1);
	}

	@Test(expected = CourseAttendenceAlreadyActive.class)
	public void testAddEnrolledStudentWhenAlreadyStudentIsInTheListThrowError() {
		Student student = createNewTestStudent("idTestStudent");
		course.addEnrolledStudent(student);
		course.addEnrolledStudent(student);

		assertEnrolledStudents(1);
	}

	@Test
	public void testMoreThanOneStudentsCanAddedToEnrollStudents() {
		course.addEnrolledStudent(createNewTestStudent("idTestStudent0"));
		course.addEnrolledStudent(createNewTestStudent("idTestStudent1"));

		assertEnrolledStudents(2);
	}

	@Test
	public void testRemoveEnrollStudent() {
		String idStudentToRemove = "idTestStudent";
		course.addEnrolledStudent(createNewTestStudent(idStudentToRemove));
		course.removeEnrolledStudent(idStudentToRemove);

		assertEnrolledStudents(0);
	}

	@Test
	public void testRemoveEnrollStudentWhenListHasMoreThanOneElement() {
		String idStudentToRemove = "idStudentToRemove";
		Student studentToRemove = createNewTestStudent(idStudentToRemove);

		course.addEnrolledStudent(createNewTestStudent("idStudentTest"));
		course.addEnrolledStudent(studentToRemove);
		course.removeEnrolledStudent(idStudentToRemove);

		assertFalse(course.getEnrolledStudent().contains(studentToRemove));
		assertEnrolledStudents(1);
	}

	@Test(expected = NoEnrolledStudentWithThisId.class)
	public void testRemoveEnrollStudentWhenIdStudentIsWrong() {
		String wrongIdStudentToRemove = "idStudentWrong";
		course.addEnrolledStudent(createNewTestStudent("idTestStudent"));
		course.removeEnrolledStudent(wrongIdStudentToRemove);

		assertEnrolledStudents(1);
	}

	@Test(expected = NoEnrolledStudentError.class)
	public void testRemoveEnrollStudentThrowErrorIfListIsEmpty() {
		course.removeEnrolledStudent("idTestStudent");
		assertEnrolledStudents(0);
	}

	@Test
	public void testAddNewStudentsCourseRequest() {
		course.addCourseRequest(createNewTestCourseRequest(createNewTestStudent("idTestStudent")));

		assertStudentCourseRequest(1);
	}

	@Test
	public void testNewCourseHasStudentsCourseRequestAreEmpty() {
		assertStudentCourseRequest(0);
	}

	@Test
	public void testRemoveCourseRequest() {
		String idStudent = "idTestStudent";

		course.addCourseRequest(createNewTestCourseRequest(createNewTestStudent(idStudent)));
		course.removeCourseRequestFromStudent("idTestStudent");

		assertStudentCourseRequest(0);
	}

	@Test(expected = NoCourseRequestActiveForThisStudent.class)
	public void testRemoveCourseRequestWhenIdOfStudentIsWrongThrowError() {
		course.addCourseRequest(createNewTestCourseRequest(createNewTestStudent("idTestStudent")));
		course.removeCourseRequestFromStudent("wrongIdOfStudent");

		assertStudentCourseRequest(1);
	}

	@Test(expected = NoStudentCourseRequestError.class)
	public void testRemoveCourseRequestWhenListIsEmptyThrowError() {
		course.removeCourseRequestFromStudent("idTestStudent");

		assertStudentCourseRequest(0);
	}

	@Test
	public void testAddCoursesAttendence() {
		course.addCourseAttendence(createNewTestCourseAttendence(createNewTestStudent("idStudentTest")));

		assertCourseAttendence(1);
	}

	@Test
	public void testNewCourseHasCoursesAttendenceEmpty() {
		assertCourseAttendence(0);
	}

	@Test
	public void testRemoveCourseAttendence() {
		String idStudent = "idStudentTest";
		course.addCourseAttendence(createNewTestCourseAttendence(createNewTestStudent(idStudent)));
		course.removeCourseAttendence(idStudent);

		assertCourseAttendence(0);
	}

	@Test (expected = NoCourseAttendenceError.class)
	public void testRemoveCourseAttendenceWhenListIsEmptyThrowError() {
		course.removeCourseAttendence("idStudentTest");
		
		assertCourseAttendence(0);
	}
	
	@Test (expected = NoCourseAttendenceError.class)
	public void testRemoveCourseAttendenceWhenIdStudentWasWrong() {
		course.addCourseAttendence(createNewTestCourseAttendence(createNewTestStudent("idStudentTest")));
		course.removeCourseAttendence("idStudentWrong");
		
		assertCourseAttendence(1);
	}

	private void assertCourseAttendence(int expected) {
		assertEquals(expected, course.getCoursesAttendence().size());
	}

	private CourseAttendence createNewTestCourseAttendence(Student student) {
		return new CourseAttendence(student, course);
	}

	private CourseRequest createNewTestCourseRequest(Student student) {
		return new CourseRequest(student, course);
	}

	private void assertStudentCourseRequest(int expected) {
		assertEquals(expected, course.getStudentsCourseRequest().size());
	}

	private void assertEnrolledStudents(int expected) {
		assertEquals(expected, course.getEnrolledStudent().size());
	}

	private Student createNewTestStudent(String idStudent) {
		return new Student("nameTestStudent", "surnameTestStudent", idStudent, mailService);
	}

	private Course createNewTestCourse(String id, String name, Teacher teacher) {
		return new Course(id, name, teacher);
	}

}
