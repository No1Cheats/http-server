package httpserver.core;

import javax.swing.text.html.HTMLDocument;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

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
			Scanner scanner = new Scanner(inputStream);
			PrintWriter writer = new PrintWriter(outputStream);
			processRequest(scanner, writer);
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

	private void processRequest(Scanner scanner, PrintWriter writer) throws IOException {

		String line = scanner.nextLine();//Do some shizzle here
		LOGGER.fine("Request line: " + line); //Second HTTP request is to ask favicon
		//Read headers....

		writer.println("HTTP/1.0 404 Not Found");


		writer.println(); //empty line for correct http
		writer.println();

		writer.println("It's wednesday my Dudes");

		writer.flush();

	}
}
