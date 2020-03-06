package httpserver.application;

import httpserver.core.protocol.HttpRequest;
import httpserver.core.protocol.HttpResponse;
import httpserver.framework.RequestHandler;

import java.util.ArrayList;

public class ToDoListHandler implements RequestHandler {

    private static ArrayList<String> myToDoList = new ArrayList<>();

    public String handleRequest(HttpRequest request, HttpResponse response) {
        if(request.getParameter("remove") != null && !myToDoList.isEmpty()){
            String item = request.getParameter("toDoItem");
            if(myToDoList.contains(item)){
                myToDoList.remove(item);
            }
            response.addParameter("toDoList", myToDoList);
            return "todo.html";
        }
        String item = request.getParameter("toDoItem");
        if(item != null){
            item = item.replaceAll("<", "!");
            myToDoList.add(item);
        }
        response.addParameter("toDoList", myToDoList);
        return "todo.html";
    }

}
