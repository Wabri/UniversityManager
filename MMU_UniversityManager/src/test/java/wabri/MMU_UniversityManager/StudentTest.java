package wabri.MMU_UniversityManager;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class StudentTest {

	private Student student;
	private MailService mailService;
	private UniversityDB universityDB;
	private Teacher teacher;

	@Before
	public void init() {
		mailService = mock(MailService.class);
		universityDB = mock(UniversityDB.class);
		student = createNewTestStudent("Name", "Surname", "ID");
		teacher = createTestTeacher("Id0");

		when(mailService.getMail(student)).thenReturn("Mail");

		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				((Student) args[0]).setIdTutor((String) args[1]);
				return null;
			}
		}).when(universityDB).studentRequestTutor(student, teacher.getId());

		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				((Student) args[0]).setIdTutor(null);
				return null;
			}
		}).when(universityDB).studentRemoveTutor(student);
	}

	@Test
	public void testNewStudentHaveData() {
		String name = "Test Name";
		String surname = "Test Surname";
		String id = "IdTest";
		student = createNewTestStudent(name, surname, id);

		assertEquals(name, student.getName());
		assertEquals(surname, student.getSurname());
		assertEquals(id, student.getId());
	}

	@Test
	public void testRequestMailWhenMailIsNull() {
		assertMailStudent("Mail");
	}

	@Test
	public void testRequestMailWhenMailIsNotNull() {
		String mail = "Test Mail";
		student.setMail(mail);

		assertMailStudent(mail);
	}

	@Test
	public void testNewStudentHasNoTutor() {
		assertEquals(null, student.getIdTutor());
	}

	@Test
	public void testNewStudentHasNoEnrolledCourse() {
		assertTrue(student.getEnrolledCourse().isEmpty());
	}

	@Test
	public void testAddSingleEnrolledCourse() {
		String id = "IdCourseTest";
		student.addEnrolledCourse(createTestCourse(id));

		assertEquals(1, student.getEnrolledCourse().size());
		assertEquals(id, student.getEnrolledCourse().get(0).getId());
	}

	@Test(expected = NoEnrolledCourseError.class)
	public void testRemoveEnrolledCourseWhenListIsEmptyThrowException() {
		student.removeEnrolledCourse("idTest");

		assertEquals(0, student.getEnrolledCourse().size());
	}

	@Test
	public void testRemoveEnrolledCourseWhenListIsNotEmpty() {
		student.addEnrolledCourse(createTestCourse("Id0"));
		student.removeEnrolledCourse("Id0");

		assertEquals(0, student.getEnrolledCourse().size());
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testRemoveEnrolledCourseWhenIdIsNotRightThrowException() {
		student.addEnrolledCourse(createTestCourse("Id0"));
		student.removeEnrolledCourse("Id1");

		assertEquals(1, student.getEnrolledCourse().size());
	}

	@Test
	public void testRemoveEnrolledCourseStopWhenFindTheRightCourse() {
		student.addEnrolledCourse(createTestCourse("ID0"));
		student.addEnrolledCourse(createTestCourse("ID1"));
		student.removeEnrolledCourse("ID1");

		assertEquals(1, student.getEnrolledCourse().size());
		assertEquals("ID0", student.getEnrolledCourse().get(0).getId());
	}

	@Test
	public void testSendTutorRequestToDB() {
		assertTutorRequest(teacher.getId());
	}

	@Test(expected = IllegalTutorRequest.class)
	public void testSendTutorRequestWhenStudentHasAlreadyATutor() {
		String idTutor = "idTutorTest";
		student.setIdTutor(idTutor);

		assertTutorRequest(idTutor);
	}

	@Test
	public void testSendTutorRemoveToDB() {
		student.setIdTutor(teacher.getId());

		assertTutorRemoveRequest();
	}

	@Test(expected = NoTutorAssignedError.class)
	public void testSendTutorRemoveToDBWhenNoTutorIsAssigned() {
		assertTutorRemoveRequest();
	}

	@Test
	public void testRequestCourseToDB() {
		Course courseRequested = createTestCourse("idTestCourse");
		List<CourseRequest> coursesRequested = new ArrayList<CourseRequest>();

		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				coursesRequested.add(new CourseRequest(((Student) args[0]), courseRequested));
				return null;
			}
		}).when(universityDB).studentRequestCourse(student, courseRequested.getId());

		student.requestCourse(courseRequested.getId());

		assertEquals(1, coursesRequested.size());
		assertEquals(courseRequested.getId(), coursesRequested.get(0).getIdCourse());
	}

	@Test(expected = RequestAlreadyActive.class)
	public void testDoubleRequestCourseToDBThrowError() {
		String idCourse = "";
		
		doThrow(RequestAlreadyActive.class).when(universityDB).studentRequestCourse(student, idCourse);
		
		student.requestCourse(idCourse);
		
		verify(universityDB, times(1)).studentRequestCourse(student, idCourse);
	}

	private void assertTutorRemoveRequest() {
		student.sendTutorRemoveRequest();

		assertEquals(null, student.getIdTutor());
	}

	private void assertTutorRequest(String expected) {
		student.sendTutorRequest(teacher.getId());

		assertEquals(expected, student.getIdTutor());
	}

	private Course createTestCourse(String id) {
		return new Course(id, "NameCourseTest", createTestTeacher("IdTeacherTest"));
	}

	private Teacher createTestTeacher(String idTeacher) {
		return new Teacher("nameTeacherTest", "surnameTeacherTest", idTeacher, mailService);
	}

	private void assertMailStudent(String expected) {
		student.requestMail();
		assertEquals(expected, student.getMail());
	}

	private Student createNewTestStudent(String name, String surname, String id) {
		return new Student(name, surname, id, mailService, universityDB);
	}

}
