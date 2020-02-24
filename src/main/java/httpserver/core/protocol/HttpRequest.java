package httpserver.core.protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

import static httpserver.core.protocol.HttpConstants.CONTENT_LENGTH_HEADER;
import static httpserver.core.protocol.HttpConstants.CONTENT_TYPE_HEADER;
import static httpserver.core.protocol.HttpConstants.FORM_CONTENT_TYPE;

/**
 * The class HttpRequest is responsible for the parsing of HTTP requests.
 */
public class HttpRequest {

	private static final Logger LOGGER = Logger.getLogger(HttpRequest.class.getName());

	private InputStream inputStream;
	private String method;
	private String uri;
	private String path;
	private String query;
	private String protocol;
	private Map<String, String> parameters = new HashMap<>();
	private Map<String, String> headers = new HashMap<>();

	public HttpRequest(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void parse() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String line = reader.readLine();
		LOGGER.fine("Request line: " + line);
		if (line != null) parseRequestLine(line);
		if (query != null) parseParameters(query);
		parseRequestHeaders(reader);
		parseFormParameters(reader);
	}

	public String getMethod() {
		return method;
	}

	public boolean isGet() {
		return method.equals(httpserver.core.protocol.HttpConstants.GET_METHOD);
	}

	public boolean isPost() {
		return method.equals(HttpConstants.POST_METHOD);
	}

	public String getUri() {
		return uri;
	}

	public String getPath() {
		return path;
	}

	public String getQuery() {
		return query;
	}

	public String getProtocol() {
		return protocol;
	}

	public Set<String> getParameterNames() {
		return parameters.keySet();
	}

	public String getParameter(String name) {
		return parameters.get(name);
	}

	public Set<String> getHeaderNames() {
		return headers.keySet();
	}

	public String getHeader(String name) {
		return headers.get(name);
	}

	private void parseRequestLine(String line) throws IOException {
		String[] parts = line.split(" ");
		method = parts[0];
		uri = parts[1];
		String[] tokens = uri.split("\\?");
		path = tokens.length > 0 ? tokens[0] : uri;
		query = tokens.length > 1 ? tokens[1] : null;
		protocol = parts[2];
	}

	private void parseParameters(String paramString) {
		String[] params = paramString.split("&");
		for (String param : params) {
			LOGGER.fine("Parameter: " + param);
			String[] tokens = param.split("=");
			try {
				String name = URLDecoder.decode(tokens[0], "UTF-8");
				String value = tokens.length > 1 ? URLDecoder.decode(tokens[1], "UTF-8") : "";
				parameters.put(name, value);
			} catch (UnsupportedEncodingException ex) {
				LOGGER.severe(ex.toString());
			}
		}
	}

	private void parseRequestHeaders(BufferedReader reader) throws IOException {
		while (true) {
			String header = reader.readLine();
			if (header == null || header.isEmpty()) break;
			LOGGER.fine("Header: " + header);
			String[] tokens = header.split(": ");
			headers.put(tokens[0], tokens[1]);
		}
	}

	private void parseFormParameters(BufferedReader reader) throws IOException {
		if (Objects.equals(headers.get(CONTENT_TYPE_HEADER), FORM_CONTENT_TYPE)) {
			int length = Integer.parseInt(headers.get(CONTENT_LENGTH_HEADER));
			char[] body = new char[length];
			reader.read(body);
			parseParameters(new String(body));
		}
	}

	@Override
	public String toString() {
		return method + " " + path + (query != null ? "?" + query : "") + " " + protocol;
	}
}
