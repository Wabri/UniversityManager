package wabri.MMU_UniversityManager.IT;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.fakemongo.Fongo;
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
		students = db.getCollection("students");
		schoolController = new SchoolController(database);
	}

	@Test
	public void testGetAllStudentsWhenThereAreNoStudents() {
		List<Student> allStudents = schoolController.getAllStudents();
		assertEquals(0, allStudents.size());
	}

}
