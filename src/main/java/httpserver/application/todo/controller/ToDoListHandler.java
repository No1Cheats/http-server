package httpserver.application.todo.controller;

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
        if(session == null){
            session = SessionManager.createSession(response);
            session.addData("toDoList", new ArrayList<String>());
        }

        List<String> myToDoList = (List<String>)session.getData("toDoList");


        String item = request.getParameter("toDoItem");
        PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
        item = policy.sanitize(item);
        if(request.getParameter("remove") != null && !myToDoList.isEmpty()){
            if(myToDoList.contains(item)){
                myToDoList.remove(item);
            }
            response.addParameter("toDoList", myToDoList);
            return "todo/todo.html";
        } else if (request.getParameter("add") != null){
            myToDoList.add(item);
        }
        response.addParameter("toDoList", myToDoList);
        return "todo/todo.html";
    }

}
