package wabri.MMU_UniversityManager;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class TeacherTest {

	Teacher teacher;
	private MailService mailService;
	private Course course;
	private UniversityDB universityDB;

	@Before
	public void init() {
		course = createNewCourse();
		mailService = mock(MailService.class);
		universityDB = mock(UniversityDB.class);
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

	@Test(expected = NoTeachCoursesError.class)
	public void testRemoveTeachCourseWhenListIsEmpty() {
		teacher.removeCourseTeach("idCourse");

		assertEquals(0, teacher.getListCoursesTeach().size());
	}

	@Test(expected = NoTeachCourseWithThisId.class)
	public void testRemoveTeachCourseWhenIdOfCourseIsNotRight() {
		teacher.addCourseTeach(course);
		teacher.removeCourseTeach("idError");

		assertEquals(1, teacher.getListCoursesTeach().size());
	}

	@Test
	public void testNewTeacherHasNoTutoredStudents() {
		assertEquals(0, teacher.getTutoredStudents().size());
	}

	@Test(expected = OutOfLimitTutoredStudents.class)
	public void testTutoredStudentsHasAMaximumOfThreeElements() {
		String idStudent0 = "idStudentTest0";
		teacher.addTutoredStudent(creteNewStudent(idStudent0));
		String idStudent1 = "idStudentTest1";
		teacher.addTutoredStudent(creteNewStudent(idStudent1));
		String idStudent2 = "idStudentTest2";
		teacher.addTutoredStudent(creteNewStudent(idStudent2));
		String idStudent3 = "idStudentTest3";
		teacher.addTutoredStudent(creteNewStudent(idStudent3));

		assertEquals(3, teacher.getTutoredStudents().size());
		assertEquals(idStudent0, teacher.getTutoredStudents().get(0));
		assertEquals(idStudent1, teacher.getTutoredStudents().get(1));
		assertEquals(idStudent2, teacher.getTutoredStudents().get(2));
	}

	@Test
	public void testAddNewTutorRequestUpdateListOfRequest() {
		TutorRequest tutorRequest = createNewTutorRequest("id0");
		teacher.addRequestedTutoring(tutorRequest);

		assertEquals(1, teacher.getRequestedTutor().size());
	}

	@Test(expected = OutOfLimitTutoredStudents.class)
	public void testAddNewTutorRequestIsNotAllowedWhenTutoredStudentAreAlreadyThree() {
		TutorRequest tutorRequest = createNewTutorRequest("idTutorRequestTest");
		teacher.addTutoredStudent(creteNewStudent("id0"));
		teacher.addTutoredStudent(creteNewStudent("id1"));
		teacher.addTutoredStudent(creteNewStudent("id2"));
		teacher.addRequestedTutoring(tutorRequest);

		assertEquals(false, teacher.getRequestedTutor().contains(tutorRequest));
	}

	@Test
	public void testAcceptTutorRequest() {
		String idStudent = "idStudent";
		teacher.addRequestedTutoring(createNewTutorRequest(idStudent));
		assertAcceptTutorRequest(idStudent, idStudent);
	}

	@Test(expected = NoTutorRequestError.class)
	public void testAcceptInexistentTutorRequest() {
		String idStudent = "idStudent";
		assertAcceptTutorRequest(null, idStudent);
	}

	@Test(expected = OutOfLimitTutoredStudents.class)
	public void testCantAcceptTutorRequestIfTutoredStudentAreAlreadyThree() {
		teacher.addTutoredStudent(creteNewStudent("id0"));
		teacher.addTutoredStudent(creteNewStudent("id1"));
		teacher.addTutoredStudent(creteNewStudent("id2"));

		assertAcceptTutorRequest("id0", "id3");
	}

	@Test
	public void testAfterAcceptedRequestSendMail() {
		String idStudent = "idTestStudent";
		TutorRequest tutorRequest = createNewTutorRequest(idStudent);

		teacher.addRequestedTutoring(tutorRequest);
		teacher.acceptTutorRequest(idStudent);

		verify(mailService, times(1)).sendMail(teacher, tutorRequest.getStudent(), "Tutoring accepted");
	}

	@Test
	public void testSendMailToAllTutoredStudents() {
		ArgumentCaptor<String> captorMail = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Student> captorStudent = ArgumentCaptor.forClass(Student.class);
		String mail = "testMail";
		String idStudent0 = "idTestStudent0";
		String idStudent1 = "idTestStudent1";
		String idStudent2 = "idTestStudent2";
		
		teacher.addTutoredStudent(creteNewStudent(idStudent0));
		teacher.addTutoredStudent(creteNewStudent(idStudent1));
		teacher.addTutoredStudent(creteNewStudent(idStudent2));
		
		teacher.sendMailToTutoredStudents(mail);

		verify(mailService, times(3)).sendMail(any(Teacher.class), captorStudent.capture(), captorMail.capture());
		
		assertEquals(captorMail.getAllValues().get(0), mail);
		assertEquals(captorMail.getAllValues().get(1), mail);
		assertEquals(captorMail.getAllValues().get(2), mail);
		assertEquals(captorStudent.getAllValues().get(0).getId(), idStudent0);
		assertEquals(captorStudent.getAllValues().get(1).getId(), idStudent1);
		assertEquals(captorStudent.getAllValues().get(2).getId(), idStudent2);
	}

	private void assertAcceptTutorRequest(String expected, String accept) {
		teacher.acceptTutorRequest(accept);

		assertEquals(expected, teacher.getTutoredStudents().get(0).getId());
	}

	private TutorRequest createNewTutorRequest(String idStudent) {
		return new TutorRequest(teacher, creteNewStudent(idStudent));
	}

	private Student creteNewStudent(String idStudent) {
		return new Student("nameStudentTest", "surnameStudentTest", idStudent, mailService, null);
	}

	private Course createNewCourse() {
		return new Course("idCourseTest", "nameCourseTest", teacher,mailService,universityDB);
	}

	private void assertMailTeacher(String expected) {
		teacher.requestMail();

		assertEquals(expected, teacher.getMail());
	}

	private Teacher createNewTestTeacher(String name, String surname, String id) {
		return new Teacher(name, surname, id, mailService);
	}
}
