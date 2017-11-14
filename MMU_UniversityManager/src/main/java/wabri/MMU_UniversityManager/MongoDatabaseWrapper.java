package wabri.MMU_UniversityManager;

import java.net.UnknownHostException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.jongo.Jongo;
import org.jongo.MongoCollection;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongoDatabaseWrapper implements Database {

	private MongoCollection students;

	public MongoDatabaseWrapper(MongoClient client) throws UnknownHostException {
		super();
		DB db = client.getDB("school");
		Jongo jongo = new Jongo(db);
		students = jongo.getCollection("student");
	}

	@Override
	public List<Student> getAllStudentsList() {
		Iterable<Student> iterable = students.find().as(Student.class);
		return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
	}

	@Override
	public Student findStudentById(String idStudent) {
		return students.findOne("{id: #}", idStudent).as(Student.class);
	}

}
