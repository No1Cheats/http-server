package httpserver.application.todo.model;

import java.util.ArrayList;
import java.util.List;

public class User {

	private String name;
	private String password;
	private List<String> todos = new ArrayList<>();

	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public List<String> getTodos() {
		return todos;
	}

	public void addTodo(String todo) {
		todos.add(todo);
	}

	public void removeTodo(String todo) {
		todos.remove(todo);
	}
}
