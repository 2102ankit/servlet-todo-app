package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStore {
	private static Map<String, Person> users = new HashMap<>();
	private static Map<Person, List<Todo>> todos = new HashMap<>();

	private static final DataStore instance = new DataStore();

	private DataStore() {

	}

	public static DataStore getInstance() {
		return instance;
	}

	public Person getUser(String email) {
		return users.get(email);
	}

	public void addUser(String email, Person person) {
		users.put(email, person);
	}

	public List<Todo> getTodos(Person person) {
		if (!todos.containsKey(person)) {
			todos.put(person, new ArrayList<Todo>());
		}
		return todos.get(person);
	}

	public void addTodo(Person person, Todo todo) {
		if (!todos.containsKey(person)) {
			todos.put(person, new ArrayList<Todo>());
		}
		List<Todo> personsTodolist = todos.get(person);
		personsTodolist.add(todo);
	}
}
