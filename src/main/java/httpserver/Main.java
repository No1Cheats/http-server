package httpserver;

import httpserver.core.HttpServer;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * The class Main is responsible for the server configuration and start.
 */
public class Main { //Connect via localhost:8080

	private static final String SERVER_CONFIG_FILE = "config/server.properties";
	private static final String LOGGING_CONFIG_FILE = "config/logging.properties";

	public static void main(String[] args) throws IOException {
		System.getProperties().load(new FileInputStream(SERVER_CONFIG_FILE));
		System.setProperty("java.util.logging.config.file", LOGGING_CONFIG_FILE);
		HttpServer server = new HttpServer(Integer.parseInt(System.getProperty("server.port")));
		server.start();
	}
}
