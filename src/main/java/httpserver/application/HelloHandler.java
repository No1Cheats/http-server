package httpserver.application;

import httpserver.core.protocol.HttpRequest;
import httpserver.core.protocol.HttpResponse;
import httpserver.framework.RequestHandler;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

public class HelloHandler implements RequestHandler {
    @Override
    public String handleRequest(HttpRequest request, HttpResponse response) {

        String cookies = request.getHeader("Cookie");
        String lang = "en";
        if (cookies != null) {
            for (String cookie : cookies.split(";")) {
                String token[] = cookie.trim().split("=");
                if (token[0].equals("lang")) {
                    lang = token[1];
                }
            }
        }

        response.addHeader("Set-Cookie", "lang=" + lang + "; Max-Age=30");

        if (request.isPost()) {
            String username = request.getParameter("username");
            PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
            username = policy.sanitize(username);
            String greeting = username == null ? "" : (lang.equals("en") ? "Hi " : "Hoi ") + username;
            response.addParameter("greeting", greeting);
            return "hello_post.html";
        }
        return "hello.html";
    }
}
