package httpserver.application.todo.model;

import java.util.HashMap;
import java.util.Map;

public class UserManager {

	private static Map<String, User> users = new HashMap<>();

	public static void register(User user) throws UserAlreadyExistsException {
		if (users.containsKey(user.getName())) {
			throw new UserAlreadyExistsException();
		}
		users.put(user.getName(), user);
	}

	public static User authenticate(String username, String password) throws InvalidCredentialsException {
		User user = users.get(username);
		if (user == null || !user.getPassword().equals(password)) {
			throw new InvalidCredentialsException();
		}
		return user;
	}
}
