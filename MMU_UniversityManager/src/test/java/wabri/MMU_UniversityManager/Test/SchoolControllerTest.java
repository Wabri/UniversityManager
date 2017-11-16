package wabri.MMU_UniversityManager.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import wabri.MMU_UniversityManager.Database;
import wabri.MMU_UniversityManager.SchoolController;
import wabri.MMU_UniversityManager.Student;

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
		students.add(newStudentTest(""));
		List<Student> allStudents = schoolController.getAllStudents();
		verify(database).getAllStudentsList();
		assertEquals(1, allStudents.size());
	}

	@Test
	public void testGetStudentByIdWhenStudentIsNotThere() {
		students.add(newStudentTest("idTest"));
		Student student = schoolController.getStudentByID("idWrong");
		verify(database).findStudentById("idWrong");
		assertNull(student);
	}

	@Test
	public void testGetStudentByIdWhenStudentIsThere() {
		String idStudent = "idTest";
		students.add(newStudentTest(idStudent));		
		when(database.findStudentById(idStudent)).thenReturn(students.get(0));
		Student student = schoolController.getStudentByID(idStudent);
		verify(database).findStudentById(idStudent);
		assertNotNull(student);
	}

	private Student newStudentTest(String idStudent) {
		return new Student("", "", idStudent, null, null);
	}

}
