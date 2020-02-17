package httpserver.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * The class HttpServer is responsible for the connection and thread management.
 */
public class HttpServer {

	private static final Logger LOGGER = Logger.getLogger(HttpServer.class.getName());

	private int serverPort;
	private ServerSocket serverSocket;
	private ExecutorService executorService;

	public HttpServer(int serverPort) {
		this.serverPort = serverPort;
	}

	public void start() throws IOException {
		serverSocket = new ServerSocket(serverPort);
		LOGGER.info("Accepting connections on port " + serverPort);
		executorService = Executors.newCachedThreadPool();
		while (true) {
			Socket socket = serverSocket.accept();
			LOGGER.info("Connected by " + socket.getInetAddress());
			socket.setSoTimeout(Integer.parseInt(System.getProperty("socket.timeout")));
			executorService.execute(new RequestWorker(socket));
		}
	}
}
