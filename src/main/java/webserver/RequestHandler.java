package webserver;

import java.io.*;
import java.net.Socket;
import fileIO.FileLoader;
import http.MyHttpRequest;
import http.MyHttpRequestParser;
import http.MyHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream();
             OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.


            MyHttpRequest newRequest = MyHttpRequestParser.parse(in);
            MyHttpResponse newResponse = new MyHttpResponse(out);
            logger.debug("MyHttpRequest is null? : {}", newRequest == null);
            if (newRequest == null) return;

            logger.debug("Body Generating");
            byte[] body = handleRequest(newRequest, newResponse);
            newResponse.setResponseBody(body);
            newResponse.send();
        } catch (IOException e) {
            logger.debug("IO EXCEPTION POPPED");
            logger.error(e.getMessage());
        }
    }

    private byte[] handleRequest(MyHttpRequest request, MyHttpResponse response) {
        String url = "";
        if (request.getMethod().equals("GET")) {
            url = getHandlerResolver(request, response);
        }
        return viewResolver(url);
    }

    private byte[] viewResolver(String url) {
        if (url.contains("static")) {
            try {
                return FileLoader.getStaticFile(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private String getHandlerResolver(MyHttpRequest request, MyHttpResponse response) {
        String url = request.getUrl();
        logger.debug("GetRequest received for : {}", url);
        String retUrl = "";
        if (url.equals("/")) {
            retUrl = "/static/index.html";
        } else if (url.contains("css")) {
            response.setHeader("Content-Type", "text/css;charset=utf-8");
            retUrl = "/static/" + url;
        } else if (url.contains("js")) {
            response.setHeader("Content-Type", "application/javascript;charset=utf-8");
            retUrl = "/static/" + url;
        } else if (url.contains("ico")) {
            response.setHeader("Content-Type", "image/x-icon");
            retUrl = "/static/" + url;
        } else if (url.contains("png")) {
            response.setHeader("Content-Type", "image/png");
            retUrl = "/static/" + url;
        } else if (url.contains("jpg") || url.contains("jpeg")) {
            response.setHeader("Content-Type", "image/jpeg");
            retUrl = "/static/" + url;
        } else if (url.contains("svg")) {
            response.setHeader("Content-Type", "image/svg+xml");
            retUrl = "/static/" + url;
        }
        else {
            retUrl = "/static/index.html";
        }

        return retUrl;
    }
}

