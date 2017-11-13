package wabri.MMU_UniversityManager;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class SchoolControllerTest {

	private SchoolController schoolController;
	private Database database;
	private List<Student> students;
	
	@Before
	public void setUp() throws Exception {
		database = mock(Database.class);
		schoolController = new SchoolController(database);
		students = new ArrayList<Student>();
		when(database.getAllStudentsList()).thenReturn(students);
	}

	@Test
	public void testGetAllStudentsWhenThereAreNoStudents() {
		List<Student> allStudents = schoolController.getAllStudents();
		verify(database).getAllStudentsList();
		assertEquals(0, allStudents.size());
	}
	
	@Test
	public void testGetAllStudentsWhenThereIsOneStudent() {
		students.add(newStudentTest());
		List<Student> allStudents = schoolController.getAllStudents();
		verify(database).getAllStudentsList();
		assertEquals(1, allStudents.size());
	}

	private Student newStudentTest() {
		return new Student("", "", "", null, null);
	}

}
