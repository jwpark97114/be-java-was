package webserver;

import fileIO.FileLoader;
import http.MyHttpRequest;
import http.MyHttpResponse;
import interfaces.HandlerMethod;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Router {

    private final Map<String, HandlerMethod> handlers ;

    public Router(Map<String, HandlerMethod> injectedHandlers){
        this.handlers = injectedHandlers;
    }

    public void handleRequest(MyHttpRequest request, MyHttpResponse response) throws InvocationTargetException, IllegalAccessException {

        String signature = extractSignature(request);
        HandlerMethod h = handlers.get(signature);
        if(h == null){
            returnStaticFiles(request,response);
            return;
        }
        h.handle(request, response);
    }

    private void returnStaticFiles(MyHttpRequest request, MyHttpResponse response) {
        if(!request.getMethod().equals("GET")){
            response.setStatus("400 Bad Request");
            response.send();
        }
        String url = request.getUrl();
        try {
            byte[] file = FileLoader.getStaticFile(url);
            response.setStatus("200 OK");
            response.setHeader("Content-Type", MimeTypeParser.getContentType(MimeTypeParser.extractExtension(url)));
            response.setHeader("Content-Length", String.valueOf(file.length));
            response.setResponseBody(file);
            response.send();
        } catch (IOException e) {
            response.setStatus("400 Bad Request");
            response.send();
        }

    }


    private String extractSignature(MyHttpRequest request){
        return request.getMethod().strip() + " " + request.getUrl().strip();
    }
}
