package httpserver.application.todo.controller;

import httpserver.application.todo.model.InvalidCredentialsException;
import httpserver.application.todo.model.User;
import httpserver.application.todo.model.UserAlreadyExistsException;
import httpserver.application.todo.model.UserManager;
import httpserver.core.protocol.HttpRequest;
import httpserver.core.protocol.HttpResponse;
import httpserver.framework.RequestHandler;
import httpserver.framework.Session;
import httpserver.framework.SessionManager;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

public class LogInHandler implements RequestHandler {

    @Override
    public String handleRequest(HttpRequest request, HttpResponse response) {

        Session session = SessionManager.getSession(request);
        if (session == null) {
            session = SessionManager.createSession(response);
        } else {
            //check if already login
        }

        if (request.isPost()) {
            if (request.getParameter("login") != null) {
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                try {
                    User user = UserManager.authenticate(username, password);
                    session.addData("user", user);
                    response.addHeader("location", "/todo");
                    response.setStatus("302");
                    return "todo/login.html";
                } catch (InvalidCredentialsException e) {
                    response.addParameter("status", "Wrong username or password. Please Try it again.");
                    e.printStackTrace();
                    return "todo/login.html";
                }
            }
            if (request.getParameter("register") != null) {
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                try {
                    User user = new User(username, password);
                    UserManager.register(user);
                    session.addData("user", user);
                    response.addHeader("location", "/todo");
                    response.setStatus("302");
                    return "todo/login.html";
                } catch (UserAlreadyExistsException e) {
                    response.addParameter("status", "User already exists. Try another name or login");
                    return "todo/login.html";
                }
            }
        } else {
            response.addParameter("status", "");
        }
        return "todo/login.html";

    }
}

