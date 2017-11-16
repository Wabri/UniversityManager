package wabri.MMU_UniversityManager.Test;

import static org.junit.Assert.*;

import java.net.UnknownHostException;

import org.junit.Before;
import org.junit.Test;

import com.github.fakemongo.Fongo;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import wabri.MMU_UniversityManager.MongoDatabaseWrapper;
import wabri.MMU_UniversityManager.Student;

public class MongoDatabaseWrapperTest {

	// SUT
	private MongoDatabaseWrapper mongoDatabase;
	// to add elements in the students table for testing
	private DBCollection students;

	@Before
	public void setUp() throws UnknownHostException {
		// in-memory java implementation of MongoDB
		// so that we don't need to install MongoDB in our computer
		Fongo fongo = new Fongo("mongo server 1");
		MongoClient mongoClient = fongo.getMongo();
		// make sure to drop the students table for testing
		DB db = mongoClient.getDB("school");
		db.getCollection("student").drop();
		mongoDatabase = new MongoDatabaseWrapper(mongoClient);
		students = db.getCollection("student");
	}

	@Test
	public void testGetAllStudentsEmpty() {
		assertTrue(mongoDatabase.getAllStudentsList().isEmpty());
	}

	@Test
	public void testGetAllStudentsNotEmpty() {
		addStudentToCollection("1", "first");
		addStudentToCollection("2", "second");
		
		assertEquals(2, mongoDatabase.getAllStudentsList().size());
	}
	
	@Test
	public void testFindStudentByIdNotFound() {
		addStudentToCollection("1", "first");
		
		assertNull(mongoDatabase.findStudentById("2"));
	}
	
	@Test
	public void testFindStudentByIdFound() {
		addStudentToCollection("1", "first");
		addStudentToCollection("2", "second");
		
		Student findStudentById = mongoDatabase.findStudentById("2");
		assertNotNull(findStudentById);
		assertEquals("2", findStudentById.getId());
		assertEquals("second", findStudentById.getName());
	}

	private void addStudentToCollection(String id, String name) {
		BasicDBObject document = new BasicDBObject();
		document.put("id", id);
		document.put("name", name);
		students.insert(document);
	}

}
