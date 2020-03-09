package httpserver.core.protocol;

/**
 * The class HttpStatus defines status codes of the HTTP protocol.
 */
public class HttpStatus {

	public static final String OK = "200 Ok";
	public static final String MOVED_PERMANENTLY = "301 Moved Permanently";
	public static final String FOUND = "302 Found";
	public static final String UNAUTHORIZED = "401 Unauthorized";
	public static final String FORBIDDEN = "403 Forbidden";
	public static final String NOT_FOUND = "404 Not Found";
	public static final String METHOD_NOT_ALLOWED = "405 Method Not Allowed";
	public static final String INTERNAL_SERVER_ERROR = "500 Internal Server Error";
	public static final String SEE_OTHER = "303 See Other";
}
