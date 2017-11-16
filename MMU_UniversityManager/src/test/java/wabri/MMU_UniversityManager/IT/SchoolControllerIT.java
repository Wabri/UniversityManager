package wabri.MMU_UniversityManager.IT;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.fakemongo.Fongo;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import wabri.MMU_UniversityManager.Database;
import wabri.MMU_UniversityManager.MongoDatabaseWrapper;
import wabri.MMU_UniversityManager.SchoolController;
import wabri.MMU_UniversityManager.Student;

public class SchoolControllerIT {

	private Database database;
	private DBCollection students;
	private SchoolController schoolController;

	@Before
	public void setUp() throws Exception {
		Fongo fongo = new Fongo("mongo server 1");
		MongoClient mongoClient = fongo.getMongo();
		DB db = mongoClient.getDB("school");
		db.getCollection("student").drop();
		database = new MongoDatabaseWrapper(mongoClient);
		students = db.getCollection("student");
		schoolController = new SchoolController(database);
	}

	@Test
	public void testGetAllStudentsWhenThereAreNoStudents() {
		assertNumberOfStudents(0);
	}
	
	@Test
	public void testGetAllStudentsWhenThereIsOne() {
		addStudent("id", "name");
		assertNumberOfStudents(1);
	}

	private void addStudent(String id, String name) {
		BasicDBObject newStudent = new BasicDBObject();
		newStudent.put("idTest", id);
		newStudent.put("nameTest", name);
		students.insert(newStudent);
	}
	
	private void assertNumberOfStudents(int expected) {
		List<Student> allStudents = schoolController.getAllStudents();
		assertEquals(expected, allStudents.size());
	}

}
