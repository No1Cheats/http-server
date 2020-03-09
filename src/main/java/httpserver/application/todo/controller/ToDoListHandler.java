package httpserver.application.todo.controller;

import httpserver.application.todo.model.User;
import httpserver.core.protocol.HttpRequest;
import httpserver.core.protocol.HttpResponse;
import httpserver.framework.RequestHandler;
import httpserver.framework.Session;
import httpserver.framework.SessionManager;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

import java.util.ArrayList;
import java.util.List;

public class ToDoListHandler implements RequestHandler {

    //private static ArrayList<String> myToDoList = new ArrayList<>();

    public String handleRequest(HttpRequest request, HttpResponse response) {
        Session session = SessionManager.getSession(request);
        User user = (User) session.getData("user");

        String item = request.getParameter("toDoItem");
        PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
        item = policy.sanitize(item);
        if (request.getParameter("remove") != null && !user.getTodos().isEmpty()) {
            if (user.getTodos().contains(item)) {
                user.removeTodo(item);
            }
            response.addParameter("toDoList", user.getTodos());
            return "todo/todo.html";
        } else if (request.getParameter("add") != null) {
            user.addTodo(item);
        }
        response.addParameter("toDoList", user.getTodos());
        return "todo/todo.html";
    }

}
