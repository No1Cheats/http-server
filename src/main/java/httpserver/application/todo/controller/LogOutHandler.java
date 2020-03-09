package httpserver.application.todo.controller;

import httpserver.core.protocol.HttpRequest;
import httpserver.core.protocol.HttpResponse;
import httpserver.framework.RequestHandler;

public class LogOutHandler implements RequestHandler {
    @Override
    public String handleRequest(HttpRequest request, HttpResponse response) {
        return "todo/logout.html";
    }
}
