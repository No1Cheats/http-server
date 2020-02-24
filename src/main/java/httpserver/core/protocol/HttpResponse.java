package httpserver.core.protocol;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static httpserver.core.protocol.HttpConstants.CONTENT_LENGTH_HEADER;

/**
 * The class HttpRequest is responsible for the writing of HTTP responses.
 */
public class HttpResponse {

	private OutputStream outputStream;
	private ByteArrayOutputStream byteStream;
	private String status = HttpStatus.OK;
	private Map<String, Object> parameters = new HashMap<>();
	private Map<String, String> headers = new HashMap<>();

	public HttpResponse(OutputStream outputStream) {
		this.outputStream = outputStream;
		this.byteStream = new ByteArrayOutputStream();
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<String> getParameterNames() {
		return parameters.keySet();
	}

	public Object getParameter(String name) {
		return parameters.get(name);
	}

	public void addParameter(String name, Object value) {
		parameters.put(name, value);
	}

	public void addHeader(String name, String value) {
		headers.put(name, value);
	}

	public void writeBody(String line) throws IOException {
		byteStream.write(line.getBytes());
	}

	public void writeBody(byte[] data) throws IOException {
		byteStream.write(data);
	}

	public void send() throws IOException {
		PrintWriter writer = new PrintWriter(outputStream);
		writer.println(HttpConstants.PROTOCOL_VERSION + " " + status);
		for (String key : headers.keySet()) {
			writer.println(key + ": " + headers.get(key));
		}
		if (byteStream.size() > 0) {
			writer.println(CONTENT_LENGTH_HEADER + ": " + byteStream.size());
		}
		writer.println();
		writer.flush();
		outputStream.write(byteStream.toByteArray());
	}
}
