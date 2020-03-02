package httpserver.framework;

import httpserver.core.protocol.HttpResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import static httpserver.core.protocol.HttpConstants.CONTENT_TYPE_HEADER;

/**
 * The class ResponseRenderer is responsible for the response rendering using templates.
 */
public class ResponseRenderer {

	private static final Logger LOGGER = Logger.getLogger(ResponseRenderer.class.getName());

	/**
	 * Reads the specified template file, replaces its placeholders by parameter values of the response,
	 * and writes the resulting content into the response body.
	 */
	public static void renderResponse(String templateName, HttpResponse response) throws IOException {
		Path path = Paths.get(System.getProperty("template.root"), templateName);
		LOGGER.info("Rendering template " + path);
		String content = new String(Files.readAllBytes(path));
		for (String name : response.getParameterNames()) {
			Object value = response.getParameter(name);
			if (value != null) {
				String replacement = value instanceof Iterable ? renderIterable((Iterable) value) : value.toString();
				content = content.replaceAll("\\{" + name + "}", replacement);
			}
		}
		content = content.replaceAll("\\{.+}", "");
		response.addHeader(CONTENT_TYPE_HEADER, "text/html");
		response.writeBody(content);
	}

	/**
	 * Renders an iterable object as list.
	 */
	private static String renderIterable(Iterable iterable) {
		StringBuilder builder = new StringBuilder("<ul>");
		for (Object item : iterable) {
			builder.append("<li>").append(item).append("</li>");
		}
		builder.append("</ul>");
		return builder.toString();
	}
}
