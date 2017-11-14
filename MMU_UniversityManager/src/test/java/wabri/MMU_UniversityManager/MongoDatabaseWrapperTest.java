package wabri.MMU_UniversityManager;

import static org.junit.Assert.*;

import java.net.UnknownHostException;

import org.junit.Before;
import org.junit.Test;

import com.github.fakemongo.Fongo;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

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

}
