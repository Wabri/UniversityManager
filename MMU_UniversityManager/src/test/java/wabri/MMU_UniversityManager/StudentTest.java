package wabri.MMU_UniversityManager;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class StudentTest {

	private Student student;
	private MailService mailService;
	private UniversityDB universityDB;
	private Teacher teacher;
	private Course course;
	private List<CourseRequest> coursesRequested;
	private List<TutorRequest> tutorsRequested;

	@Before
	public void init() {
		mailService = mock(MailService.class);
		universityDB = mock(UniversityDB.class);
		student = createNewTestStudent("Name", "Surname", "ID");
		teacher = createTestTeacher("Id0");
		course = createTestCourse("idTestCourse");
		coursesRequested = new ArrayList<CourseRequest>();
		tutorsRequested = new ArrayList<TutorRequest>();

		when(mailService.getMail(student)).thenReturn("Mail");

		when(universityDB.findTeacherWithId(teacher.getId())).thenReturn(teacher);

		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				TutorRequest tempTutorRequest = new TutorRequest(universityDB.findTeacherWithId((String) args[1]),
						((Student) args[0]));
				tutorsRequested.add(tempTutorRequest);
				return null;
			}
		}).doThrow(RequestAlreadyActive.class).when(universityDB).studentRequestTutor(student, teacher.getId());

		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				((Student) args[0]).setIdTutor(null);
				return null;
			}
		}).when(universityDB).studentRemoveTutor(student);

		universityDBDoAnswerWithCourseRequest(course);

		universityDBDoAnswerWithCourseRemoveRequest(course.getId());
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

	@Test(expected = NoEnrolledCourseWithThisId.class)
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
		assertTutorRequest(teacher.getId(), "");
	}

	@Test(expected = IllegalTutorRequest.class)
	public void testSendTutorRequestWhenStudentHasAlreadyATutor() {
		String idTutor = "idTutorTest";
		student.setIdTutor(idTutor);

		assertTutorRequest(idTutor, "");
	}

	@Test
	public void testSendTutorRemoveToDB() {
		student.setIdTutor(teacher.getId());

		assertTutorRemoveRequest();
	}

	@Test
	public void testSendTutorRequestIfSuccessSendMailToTeacher() {
		String message = "mail";
		assertTutorRequest(teacher.getId(), message);

		ArgumentCaptor<String> mailCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
		ArgumentCaptor<Teacher> teacherCaptor = ArgumentCaptor.forClass(Teacher.class);

		verify(mailService, times(1)).sendMail(studentCaptor.capture(), teacherCaptor.capture(), mailCaptor.capture());

		assertEquals(message, mailCaptor.getAllValues().get(0));
		assertEquals(student, studentCaptor.getAllValues().get(0));
		assertEquals(teacher, teacherCaptor.getAllValues().get(0));
	}

	@Test(expected = IllegalTutorRequest.class)
	public void testSendTutorRequestWhenIdTeacherWasWrong() {
		String idTeacher = "idWrongTutor";

		assertWrongTutorRequest(idTeacher);
	}

	@Test
	public void testSendTutorRequestWhenIdTeacherWasWrongDoNotUpdateTutorOfStudent() {
		String idTeacher = "idWrongTutor";

		try {
			assertWrongTutorRequest(idTeacher);
		} catch (IllegalTutorRequest e) {
			assertEquals(null, student.getIdTutor());
			verify(mailService, times(0)).sendMail(any(Student.class), any(Teacher.class), anyString());
		}
	}

	@Test
	public void testSendMailToTutor() {
		student.setIdTutor(teacher.getId());
		student.sendMailToTutor("");

		verify(mailService, times(1)).sendMail(student, teacher, "");
	}

	@Test
	public void testSendMailToTutorMustHaveAMessage() {
		String mail = "Mail";

		student.setIdTutor(teacher.getId());
		student.sendMailToTutor(mail);

		ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

		verify(mailService, times(1)).sendMail(any(Student.class), any(Teacher.class), messageCaptor.capture());

		assertEquals(mail, messageCaptor.getAllValues().get(0));
	}

	@Test(expected = RequestAlreadyActive.class)
	public void testSendTutorRequestThrowErrorIfThereIsSameTutorRequestInDatabase() {
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				tutorsRequested.add(new TutorRequest((Teacher) args[0], (Student) args[1]));
				return null;
			}
		}).when(universityDB).createTutorRequest(teacher, student);

		universityDB.createTutorRequest(teacher, student);
		
		doThrow(RequestAlreadyActive.class).when(universityDB).studentRequestTutor(student, teacher.getId());

		student.sendTutorRequest(teacher.getId(), "");
	}

	@Test(expected = NoTutorAssignedError.class)
	public void testSendTutorRemoveToDBWhenNoTutorIsAssigned() {
		assertTutorRemoveRequest();
	}

	@Test
	public void testRequestCourseToDB() {
		student.requestEnrollingCourse(course.getId());

		assertEquals(1, coursesRequested.size());
		assertEquals(course.getId(), coursesRequested.get(0).getIdCourse());
	}

	@Test(expected = RequestAlreadyActive.class)
	public void testDuplicateRequestCourseToDBThrowError() {
		String idCourse = "idTestCourse";
		course = createTestCourse(idCourse);

		student.requestEnrollingCourse(idCourse);
		student.requestEnrollingCourse(idCourse);

		verify(universityDB, times(2)).studentRequestCourse(student, idCourse);
		assertEquals(1, coursesRequested.size());
	}

	@Test
	public void testStudentCanRequestMoreThanOneCourse() {
		Course[] courseRequested0 = { createTestCourse("id0"), createTestCourse("id1") };

		universityDBDoAnswerWithCourseRequest(courseRequested0[0]);

		universityDBDoAnswerWithCourseRequest(courseRequested0[1]);

		student.requestEnrollingCourse(courseRequested0[0].getId());
		student.requestEnrollingCourse(courseRequested0[1].getId());

		assertEquals(2, coursesRequested.size());
		assertEquals(courseRequested0[0].getId(), coursesRequested.get(0).getIdCourse());
		assertEquals(courseRequested0[1].getId(), coursesRequested.get(1).getIdCourse());
	}

	@Test
	public void testStudentWantToRemoveEnrolledCourse() {
		String id = course.getId();
		student.addEnrolledCourse(createTestCourse(id));

		student.requestRemoveEnrolledCourse(id);

		verify(universityDB, times(1)).studentRemoveCourse(student, id);

		assertEquals(0, student.getEnrolledCourse().size());
	}

	@Test(expected = RequestAlreadyActive.class)
	public void testSendTutorRequestWhenAlreadyStudentRequestOne() {
		student.sendTutorRequest(teacher.getId(), "");
		student.sendTutorRequest(teacher.getId(), "");
	}

	private void assertWrongTutorRequest(String idTeacher) throws IllegalTutorRequest {
		doThrow(NoTeacherFound.class).when(universityDB).findTeacherWithId(idTeacher);

		student.sendTutorRequest(idTeacher, "");
	}

	private void universityDBDoAnswerWithCourseRemoveRequest(String idCourseToRemove) {
		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				((Student) args[0]).removeEnrolledCourse((String) args[1]);
				return null;
			}
		}).when(universityDB).studentRemoveCourse(student, idCourseToRemove);
	}

	private void universityDBDoAnswerWithCourseRequest(Course withCourseRequested) {
		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				coursesRequested.add(new CourseRequest(((Student) args[0]), withCourseRequested));
				return null;
			}
		}).doThrow(RequestAlreadyActive.class).when(universityDB).studentRequestCourse(student,
				withCourseRequested.getId());
	}

	private void assertTutorRemoveRequest() {
		student.sendTutorRemoveRequest();

		assertEquals(null, student.getIdTutor());
	}

	private void assertTutorRequest(String expected, String message) {
		student.sendTutorRequest(teacher.getId(), message);

		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				TutorRequest tutorRequest = (TutorRequest) args[0];
				Student tempStud = tutorRequest.getStudent();
				tempStud.setIdTutor(tutorRequest.getIdTeacher());
				return null;
			}
		}).when(universityDB).createTutoring(tutorsRequested.get(0));

		universityDB.createTutoring(tutorsRequested.get(0));

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
