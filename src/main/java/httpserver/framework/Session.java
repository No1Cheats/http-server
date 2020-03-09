package httpserver.framework;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The class Session contains the identifier, data and access time of a client session.
 */
public class Session {

	private static Long timeout;

	private final String id;
	private final Map<String, Object> data = new HashMap<>();
	private Date lastAccess;

	static {
		try {
			timeout = Long.parseLong(System.getProperty("session.timeout"));
		} catch (NumberFormatException ex) {
		}
	}

	public Session(String id) {
		this.id = id;
		lastAccess = new Date();
	}

	public String getId() {
		lastAccess = new Date();
		return id;
	}

	public boolean containsData(String name) {
		lastAccess = new Date();
		return data.containsKey(name);
	}

	public Object getData(String name) {
		lastAccess = new Date();
		return data.get(name);
	}

	public void addData(String name, Object object) {
		lastAccess = new Date();
		data.put(name, object);
	}

	public boolean hasExpired() {
		return timeout != null ? new Date().getTime() - lastAccess.getTime() > timeout : false;
	}
}
