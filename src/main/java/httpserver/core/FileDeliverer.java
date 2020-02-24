package httpserver.core;

import httpserver.core.protocol.HttpResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import static httpserver.core.protocol.HttpConstants.CONTENT_TYPE_HEADER;

/**
 * The class FileDeliverer is responsible for the delivery of static file content.
 */
public class FileDeliverer {

	private static final Logger LOGGER = Logger.getLogger(FileDeliverer.class.getName());

	public static boolean deliverFile(String path, HttpResponse response) throws IOException {
		if (path.endsWith("/")) {
			path += System.getProperty("index.file");
		}
		Path filepath = Paths.get(System.getProperty("document.root"), path);
		if (!Files.exists(filepath) || !Files.isRegularFile(filepath)) {
			return false;
		}
		LOGGER.info("Delivering file " + filepath);
		response.addHeader(CONTENT_TYPE_HEADER, Files.probeContentType(filepath));
		response.writeBody(Files.readAllBytes(filepath));
		return true;
	}
}
