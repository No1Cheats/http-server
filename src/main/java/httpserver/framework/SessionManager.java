package httpserver.framework;

import httpserver.core.protocol.HttpRequest;
import httpserver.core.protocol.HttpResponse;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static httpserver.core.protocol.HttpConstants.COOKIE_HEADER;
import static httpserver.core.protocol.HttpConstants.SET_COOKIE_HEADER;

/**
 * The class SessionManager is responsible for the management of client sessions.
 */
public class SessionManager {

	private static Map<String, Session> sessions = new HashMap<>();
	private static final Logger LOGGER = Logger.getLogger(SessionManager.class.getName());

	/**
	 * Creates a new session and adds a cookie header with its identifier.
	 */
	public static Session createSession(HttpRequest request, HttpResponse response) {
		String id = createSessionId();
		LOGGER.info("Creating session with id " + id);
		Session session = new Session(id);
		sessions.put(id, session);
		response.addHeader(SET_COOKIE_HEADER, "session-id=" + id);
		return session;
	}

	/**
	 * Returns the session identified by the cookie header.
	 */
	public static Session getSession(HttpRequest request, HttpResponse response) {
		String id = getSessionId(request);
		if (id == null) return null;
		LOGGER.info("Getting session with id " + id);
		Session session = sessions.get(id);
		if (session == null) {
			LOGGER.info("Session with id " + id + " not found");
			return null;
		} else if (session.hasExpired()) {
			LOGGER.info("Session with id " + id + " has expired");
			return null;
		}
		return session;
	}

	/**
	 * Deletes the session identified by the cookie header.
	 */
	public static void deleteSession(HttpRequest request, HttpResponse response) {
		String id = getSessionId(request);
		if (id != null) {
			LOGGER.info("Deleting session with id " + id);
			sessions.remove(id);
		}
	}

	/**
	 * Creates a random session identifier.
	 */
	private static String createSessionId() {
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[10];
		random.nextBytes(bytes);
		return new BigInteger(bytes).abs().toString(16);
	}

	/**
	 * Gets the session identifier from the cookie header.
	 */
	private static String getSessionId(HttpRequest request) {
		String cookies = request.getHeader(COOKIE_HEADER);
		if (cookies == null) return null;
		for (String cookie : cookies.split(";")) {
			String tokens[] = cookie.trim().split("=");
			if (tokens[0].equals("session-id") && tokens.length > 1) {
				return tokens[1];
			}
		}
		return null;
	}
}
