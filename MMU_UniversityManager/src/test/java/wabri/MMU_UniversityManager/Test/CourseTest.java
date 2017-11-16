package wabri.MMU_UniversityManager.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import wabri.MMU_UniversityManager.Course;
import wabri.MMU_UniversityManager.CourseAttendence;
import wabri.MMU_UniversityManager.CourseAttendenceAlreadyActive;
import wabri.MMU_UniversityManager.CourseRequest;
import wabri.MMU_UniversityManager.MailService;
import wabri.MMU_UniversityManager.NoCourseAttendenceError;
import wabri.MMU_UniversityManager.NoCourseRequestActiveForThisStudent;
import wabri.MMU_UniversityManager.NoEnrolledStudentError;
import wabri.MMU_UniversityManager.NoEnrolledStudentWithThisId;
import wabri.MMU_UniversityManager.NoStudentCourseRequestError;
import wabri.MMU_UniversityManager.Student;
import wabri.MMU_UniversityManager.Teacher;
import wabri.MMU_UniversityManager.UniversityDB;

public class CourseTest {

	private Course course;
	private Teacher teacher;
	private MailService mailService;
	private UniversityDB universityDB;

	@Before
	public void init() {
		teacher = createNewTestTeacher("idTeacher");
		mailService = mock(MailService.class);
		universityDB = mock(UniversityDB.class);
		
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
		assertEquals(teacher.getMail(), course.getMail());
	}

	@Test
	public void testCourseHasGetIdTeacher() {
		assertEquals(teacher.getId(), course.getIdTeacher());
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

	@Test(expected = NoCourseAttendenceError.class)
	public void testRemoveCourseAttendenceWhenListIsEmptyThrowError() {
		course.removeCourseAttendence("idStudentTest");

		assertCourseAttendence(0);
	}

	@Test(expected = NoCourseAttendenceError.class)
	public void testRemoveCourseAttendenceWhenIdStudentWasWrong() {
		course.addCourseAttendence(createNewTestCourseAttendence(createNewTestStudent("idStudentTest")));
		course.removeCourseAttendence("idStudentWrong");

		assertCourseAttendence(1);
	}

	@Test
	public void testSetNewTeacherToTheCourse() {
		String idTeacher = "idNewTeacherTest";
		Teacher newTeacher = createNewTestTeacher(idTeacher);

		course.setTeacher(teacher);

		assertReplacingTeacher(idTeacher, newTeacher);
	}

	@Test
	public void testIfReplacingTeacherSuccessSendMailToBothTeacher() {
		String idTeacher = "idNewTeacherTest";
		Teacher newTeacher = createNewTestTeacher(idTeacher);

		course.setTeacher(teacher);

		assertReplacingTeacher(idTeacher, newTeacher);

		ArgumentCaptor<Teacher> captorTeacher = ArgumentCaptor.forClass(Teacher.class);
		ArgumentCaptor<String> captorMail = ArgumentCaptor.forClass(String.class);

		verify(mailService, times(2)).sendMail(any(Course.class), captorTeacher.capture(), captorMail.capture());

		assertEquals(teacher, captorTeacher.getAllValues().get(0));
		assertEquals(newTeacher, captorTeacher.getAllValues().get(1));
		assertEquals(newTeacher.getId() + " replaced in course " + course.getId(), captorMail.getAllValues().get(0));
		assertEquals("You are the new teacher of " + course.getId(), captorMail.getAllValues().get(1));
	}
	
	@Test
	public void testAcceptingCourseRequestFromStudent() {
		String idStudent = "idTestStudent";
		assertAcceptCourseRequest(createNewTestStudent(idStudent));
	}
	
	@Test
	public void testSendMailWhenCourseRequestWasAccepted() {
		Student student = createNewTestStudent("idTestStudent");
		assertAcceptCourseRequest(student);
		
		verify(mailService, times(1)).sendMail(course, student, "accept course request");
	}
	
	private void assertAcceptCourseRequest(Student student) {
		course.addCourseRequest(createNewTestCourseRequest(student));
		course.acceptCourseRequestFromStudent(student.getId());
		
		assertEquals(1, course.getCoursesAttendence().size());
		assertEquals(student.getId(), course.getCoursesAttendence().get(0).getIdStudent());
		assertEquals(course.getId(), course.getCoursesAttendence().get(0).getIdCourse());
		assertEquals(1, course.getEnrolledStudent().size());
		assertEquals(student.getId(), course.getEnrolledStudent().get(0).getId());
		assertEquals(0, course.getStudentsCourseRequest().size());
	}

	private void assertReplacingTeacher(String idTeacher, Teacher newTeacher) {
		course.replaceTeacher(newTeacher);

		assertEquals(idTeacher, course.getIdTeacher());
	}

	private Teacher createNewTestTeacher(String idTeacher) {
		return new Teacher("nameTeacherTest", "surnameTeacherTest", idTeacher, mailService);
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
		return new Student("nameTestStudent", "surnameTestStudent", idStudent, mailService, null);
	}

	private Course createNewTestCourse(String id, String name, Teacher teacher) {
		return new Course(id, name, teacher, mailService, universityDB);
	}

}
