package httpserver.application;

import httpserver.core.protocol.HttpRequest;
import httpserver.core.protocol.HttpResponse;
import httpserver.framework.RequestHandler;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeHandler implements RequestHandler {
    @Override
    public String handleRequest(HttpRequest request, HttpResponse response) {
        Date date = new Date();
        response.addParameter("date", date);
        return "time.html";
    }
}
