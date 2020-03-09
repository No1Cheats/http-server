package httpserver.core;

import httpserver.application.todo.model.InvalidCredentialsException;
import httpserver.application.todo.model.UserAlreadyExistsException;
import httpserver.core.protocol.HttpConstants;
import httpserver.core.protocol.HttpRequest;
import httpserver.core.protocol.HttpResponse;
import httpserver.core.protocol.HttpStatus;
import httpserver.framework.FrontController;

import javax.swing.text.html.HTMLDocument;
import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Logger;

import static httpserver.core.protocol.HttpConstants.CONTENT_TYPE_HEADER;

/**
 * The class RequestWorker is responsible for the processing of HTTP requests.
 */
public class RequestWorker implements Runnable {

	private static final Logger LOGGER = Logger.getLogger(RequestWorker.class.getName());

	private Socket socket;

	public RequestWorker(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try (InputStream inputStream = socket.getInputStream(); OutputStream outputStream = socket.getOutputStream()) {
			HttpRequest request = new HttpRequest(inputStream);
			HttpResponse response = new HttpResponse(outputStream);
			request.parse();
			processRequest(request,response);
			response.send();
		} catch (IOException ex) {
			LOGGER.severe(ex.toString());
		} finally {
			try {
				socket.close();
				LOGGER.info("Connection to " + socket.getInetAddress() + " closed");
			} catch (IOException ex) {
			}
		}
	}

	private void processRequest(HttpRequest request, HttpResponse response) throws IOException {

		boolean success = FileDeliverer.deliverFile(request.getPath(), response);

		if(!success){
			try {
				success = FrontController.processRequest(request, response);
			} catch (RuntimeException ex){
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			} catch (UserAlreadyExistsException e) {
				e.printStackTrace();
			} catch (InvalidCredentialsException e) {
				e.printStackTrace();
			}

		}

		if (!success){
			response.setStatus(HttpStatus.NOT_FOUND);
			response.writeBody("<html><h1>Not found</h1></html>");
		}

	}
}
