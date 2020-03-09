package httpserver.framework;

import httpserver.application.todo.model.InvalidCredentialsException;
import httpserver.application.todo.model.UserAlreadyExistsException;
import httpserver.core.protocol.HttpRequest;
import httpserver.core.protocol.HttpResponse;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * The class FrontController is responsible for the dynamic request processing.
 */
public class FrontController {

	private static final String ROUTES_FILE = "config/routes.properties";
	private static final Logger LOGGER = Logger.getLogger(FrontController.class.getName());

	/**
	 * Gets the request handler associated with the request path, lets it handle the HTTP request,
	 * and renders the template whose name is returned by the handler.
	 */
	public static boolean processRequest(HttpRequest request, HttpResponse response) throws IOException, UserAlreadyExistsException, InvalidCredentialsException {
		RequestHandler handler = getRequestHandler(request.getPath());
		if (handler == null) return false;
		LOGGER.info("Dispatching request to " + handler.getClass().getName());
		String templateName = handler.handleRequest(request, response);
		if (templateName != null) {
			ResponseRenderer.renderResponse(templateName, response);
		}
		return true;
	}

	/**
	 * Reads the routes file and tries to load the request handler associated with the specified path.
	 */
	private static RequestHandler getRequestHandler(String path) throws IOException {
		Properties routes = new Properties();
		routes.load(new FileInputStream(ROUTES_FILE));
		String classname = routes.getProperty(path);
		if (classname == null) return null;
		try {
			Class clazz = Class.forName(classname);
			return (RequestHandler) clazz.getDeclaredConstructor().newInstance();
		} catch (ReflectiveOperationException ex) {
			return null;
		}
	}
}
