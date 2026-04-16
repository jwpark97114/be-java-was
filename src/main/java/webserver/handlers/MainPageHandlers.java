package webserver.handlers;

import annotations.RequestMapping;
import jhttp.HttpRequest;
import jhttp.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.session.Session;
import webserver.session.SessionManager;


import java.io.IOException;

public class MainPageHandlers {
    private static final Logger logger = LoggerFactory.getLogger(MainPageHandlers.class);

    @RequestMapping(method = "GET", path = "/")
    public void getFrontPage(HttpRequest request, HttpResponse response, Session session) throws IOException {
        if(session == null){
            logger.debug("NULL IN LOGIN SESSION");
            response.setHeader("Set-Cookie", "SID="+ request.getSessionID() +"; Path=/; Max-Age=0; HttpOnly");
            response.sendHtml("/index.html");
            return;
        }

        response.sendHtml("/index.html");
    }

}
