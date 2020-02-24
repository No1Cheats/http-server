package httpserver.core.protocol;

/**
 * The class HttpConstants defines constants of the HTTP protocol.
 */
public class HttpConstants {

	public static final String PROTOCOL_VERSION = "HTTP/1.0";
	public static final String GET_METHOD = "GET";
	public static final String POST_METHOD = "POST";
	public static final String FORM_CONTENT_TYPE = "application/x-www-form-urlencoded";
	public static final String AUTH_SCHEME = "Basic";

	public static final String AUTHENTICATE_HEADER = "WWW-Authenticate";
	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String CONTENT_LENGTH_HEADER = "Content-Length";
	public static final String CONTENT_TYPE_HEADER = "Content-Type";
	public static final String COOKIE_HEADER = "Cookie";
	public static final String SET_COOKIE_HEADER = "Set-Cookie";
	public static final String LOCATION_HEADER = "Location";
}
