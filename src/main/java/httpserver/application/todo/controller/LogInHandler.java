package httpserver.application.todo.controller;

import httpserver.application.todo.model.InvalidCredentialsException;
import httpserver.application.todo.model.User;
import httpserver.application.todo.model.UserAlreadyExistsException;
import httpserver.application.todo.model.UserManager;
import httpserver.core.protocol.HttpRequest;
import httpserver.core.protocol.HttpResponse;
import httpserver.core.protocol.HttpStatus;
import httpserver.framework.RequestHandler;
import httpserver.framework.Session;
import httpserver.framework.SessionManager;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

import java.util.ArrayList;

public class LogInHandler implements RequestHandler {
    @Override
    public String handleRequest(HttpRequest request, HttpResponse response) throws UserAlreadyExistsException, InvalidCredentialsException {
        UserManager myManager = new UserManager();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
        username = policy.sanitize(username);
        password = policy.sanitize(password);
        User user = new User(username,password);
        if (request.getParameter("register") != null){
            myManager.register(user);
            Session session = SessionManager.getSession(request);
            if (session == null) {
                session = SessionManager.createSession(response);
            }
            session.addData("user", user);
            return "todo/login.html";
        } else if (request.getParameter("login") != null){
            if(myManager.authenticate(username,password) != null){
                response.addHeader("location", "/todo");
                response.setStatus("302");
            }
        }
        return "todo/login.html";
    }
}

