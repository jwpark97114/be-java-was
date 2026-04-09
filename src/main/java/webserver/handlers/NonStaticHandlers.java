package webserver.handlers;

import annotations.MyRequestMapping;
import fileIO.FileLoader;
import http.MyHttpRequest;
import http.MyHttpResponse;

import java.io.IOException;

public class NonStaticHandlers {

    @MyRequestMapping(method = "GET", path = "/")
    public void getFrontPage(MyHttpRequest request, MyHttpResponse response) throws IOException {
        response.setStatus("200 OK");
        response.setHeader("Content-Type","text/html;charset=utf-8");
        byte[] body = FileLoader.getStaticFile("/index.html");
        response.setHeader("Content-Length",String.valueOf(body.length));
        response.setResponseBody(body);
        response.send();
    }

    @MyRequestMapping(method = "GET", path = "/registration")
    public void getRegistrationPage(MyHttpRequest request, MyHttpResponse response) throws IOException{
        response.setStatus("200 OK");
        response.setHeader("Content-Type","text/html;charset=utf-8");
        byte[] body = FileLoader.getStaticFile("/registration/index.html");
        response.setHeader("Content-Length",String.valueOf(body.length));
        response.setResponseBody(body);
        response.send();
    }

}
