package httpserver.application.todo.controller;

import httpserver.application.todo.model.InvalidCredentialsException;
import httpserver.application.todo.model.User;
import httpserver.application.todo.model.UserManager;
import httpserver.core.protocol.HttpRequest;
import httpserver.core.protocol.HttpResponse;
import httpserver.framework.RequestHandler;
import httpserver.framework.Session;
import httpserver.framework.SessionManager;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

public class ToDoListHandler implements RequestHandler {

    public String handleRequest(HttpRequest request, HttpResponse response) {
        Session session = SessionManager.getSession(request);
        if (session == null) {
            response.setStatus("302");
            response.addHeader("location", "/todo/login");
            return "todo/todo.html";
        }

        User user = (User) session.getData("user");
        if (user == null) {
            response.setStatus("302");
            response.addHeader("location", "/todo/login");
            return "todo/todo.html";
        }

        try {
            UserManager.authenticate(user.getName(), user.getPassword());
        } catch (InvalidCredentialsException e) {
            response.setStatus("302");
            response.addHeader("location", "/todo/login");
            return "todo/todo.html";
        }
        String task;
        PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
        if (request.getParameter("remove") != null) {
            task = request.getParameter("toDoItem");
            task = policy.sanitize(task);
            if (user.getTodos().contains(task)) {
                user.removeTodo(task);
            }
        }
        if (request.getParameter("add") != null) {
            task = request.getParameter("toDoItem");
            task = policy.sanitize(task);
            user.addTodo(task);
        }
        if(request.getParameter("logout") != null){
            response.setStatus("302");
            response.addHeader("location", "/todo/logout");
            return "todo/todo.html";
        }

        response.addParameter("task", user.getTodos());
        return "todo/todo.html";
    }

}
