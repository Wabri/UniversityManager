package wabri.MMU_UniversityManager;

import java.net.UnknownHostException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoDatabaseWrapper implements Database {

	private DBCollection students;

	public MongoDatabaseWrapper(MongoClient client) throws UnknownHostException {
		super();
		DB db = client.getDB("school");
		students = db.getCollection("student");
	}

	@Override
	public List<Student> getAllStudentsList() {
		DBCursor cursor = students.find();
		return StreamSupport.stream(cursor.spliterator(), false)
				.map(e -> new Student((String) e.get("name"), (String) e.get("surname"), (String) e.get("id"),
						(MailService) e.get("mailService"), (UniversityDB) e.get("universityDB")))
				.collect(Collectors.toList());
	}

	@Override
	public Student findStudentById(String idStudent) {
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("id", idStudent);
		DBObject findOne = students.findOne(searchQuery);
		return findOne != null
				? new Student((String) findOne.get("name"), (String) findOne.get("surname"), (String) findOne.get("id"),
						(MailService) findOne.get("mailService"), (UniversityDB) findOne.get("universityDB"))
				: null;
	}

}
