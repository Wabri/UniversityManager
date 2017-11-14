package wabri.MMU_UniversityManager;

import static org.junit.Assert.*;

import java.net.UnknownHostException;

import org.junit.Before;
import org.junit.Test;

import org.jongo.Jongo;
import org.jongo.MongoCollection;

import com.github.fakemongo.Fongo;
import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongoDatabaseWrapperTest {

	private MongoDatabaseWrapper mongoDatabase;
	private MongoCollection students;

	@Before
	public void setUp() throws UnknownHostException {
		Fongo fongo = new Fongo("mongo server 1");
		MongoClient mongoClient = fongo.getMongo();
		DB db = mongoClient.getDB("school");
		db.getCollection("student").drop();
		mongoDatabase = new MongoDatabaseWrapper(mongoClient);
		Jongo jongo = new Jongo(db);
		students = jongo.getCollection("student");
	}

	@Test
	public void testGetAllStudentsEmpty() {
		assertTrue(mongoDatabase.getAllStudentsList().isEmpty());
	}

	@Test
	public void testGetAllStudentsNotEmpty() {
		students.insert("{id: '1',name: 'first'}");
		students.insert("{id: '1',name: 'first'}");

		assertEquals(2, mongoDatabase.getAllStudentsList().size());
	}

	@Test
	public void testFindStudentByIdNotFound() {
		students.insert("{id: '1',name: 'first'}");

		assertNull(mongoDatabase.findStudentById("2"));
	}

	@Test
	public void testFindStudentByIdFound() {
		students.insert("{id: '1',name: 'first'}");
		students.insert("{id: '2',name: 'second'}");

		Student findStudentById = mongoDatabase.findStudentById("2");
		assertNotNull(findStudentById);
		assertEquals("2", findStudentById.getId());
		assertEquals("second", findStudentById.getName());
	}

}
