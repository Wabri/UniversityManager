package wabri.MMU_UniversityManager;

import java.util.List;

public class SchoolController {

	private Database database;

	public SchoolController(Database database) {
		this.database = database;
	}

	public List<Student> getAllStudents() {
		return database.getAllStudentsList();
	}

	public Student getStudentByID(String idStudent) {
		return database.findStudentById(idStudent);
	}

}
