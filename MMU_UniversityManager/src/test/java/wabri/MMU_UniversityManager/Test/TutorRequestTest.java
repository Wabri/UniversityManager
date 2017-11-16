package wabri.MMU_UniversityManager.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import wabri.MMU_UniversityManager.Student;
import wabri.MMU_UniversityManager.Teacher;
import wabri.MMU_UniversityManager.TutorRequest;

public class TutorRequestTest {

	private TutorRequest tutorRequest;
	private Teacher teacher;
	private Student student;
	
	@Before
	public void init() {
		teacher = mock(Teacher.class);
		student = mock(Student.class);
		tutorRequest = createNewTestTutorRequest(teacher,student);
		
		when(teacher.getId()).thenReturn("idTeacher");
		when(student.getId()).thenReturn("idStudent");
	}
	
	@Test
	public void testTutorRequestHasReferenceOfStudent() {
		assertEquals(student.getId(), tutorRequest.getIdStudent());
	}
	
	@Test
	public void testTutorRequestHasReferenceOfTeacher() {
		assertEquals(teacher.getId(), tutorRequest.getIdTeacher());
	}
	
	private TutorRequest createNewTestTutorRequest(Teacher teacher, Student student) {
		return new TutorRequest(teacher,student);
	}

}
