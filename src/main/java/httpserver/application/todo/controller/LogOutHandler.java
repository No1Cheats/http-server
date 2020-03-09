package httpserver.application.todo.controller;

import httpserver.application.todo.model.User;
import httpserver.core.protocol.HttpRequest;
import httpserver.core.protocol.HttpResponse;
import httpserver.framework.RequestHandler;
import httpserver.framework.Session;
import httpserver.framework.SessionManager;

public class LogOutHandler implements RequestHandler {
    @Override
    public String handleRequest(HttpRequest request, HttpResponse response) {

        Session session = SessionManager.getSession(request);
        if (session == null) {
            System.out.println("Session == null");
            response.setStatus("302");
            response.addHeader("location", "/todo/login");
            return "todo/logout.html";
        }
        User user = (User) session.getData("user");
        if (user == null) {
            System.out.println("zweites Sorry");
            response.setStatus("302");
            response.addHeader("location", "/todo/login");
            return "todo/logout.html";
        }

        SessionManager.deleteSession(request, response);

        return "todo/logout.html";
    }
}
