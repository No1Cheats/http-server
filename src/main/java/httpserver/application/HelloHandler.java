package httpserver.application;

import httpserver.core.protocol.HttpRequest;
import httpserver.core.protocol.HttpResponse;
import httpserver.framework.RequestHandler;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

public class HelloHandler implements RequestHandler {
    @Override
    public String handleRequest(HttpRequest request, HttpResponse response) {
        if(request.isPost()){
            String username = request.getParameter("username");
            PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
            username = policy.sanitize(username);
            String greeting = username == null ? "" : "Hi " + username;
            response.addParameter("greeting", greeting);
            return "hello_post.html";
        }
        return "hello.html";
    }
}
