package httpserver.framework;

import httpserver.application.todo.model.InvalidCredentialsException;
import httpserver.application.todo.model.UserAlreadyExistsException;
import httpserver.core.protocol.HttpRequest;
import httpserver.core.protocol.HttpResponse;

/**
 * The interface RequestHandler is used for the handling of specific requests.
 */
public interface RequestHandler {

	/**
	 * Handles an HTTP request, writes the response and returns the name of the template to render.
	 */
	String handleRequest(HttpRequest request, HttpResponse response) throws UserAlreadyExistsException, InvalidCredentialsException;
}
