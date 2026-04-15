package webserver.handlers;

import annotations.RequestMapping;
import db.Database;
import fileIO.FileLoader;
import jhttp.HttpRequest;
import jhttp.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.MimeTypeParser;

import java.io.IOException;

import static auth.JUserAuth.hashPassword;

public class LoginHandlers {

    private static final Logger logger = LoggerFactory.getLogger(LoginHandlers.class);

    @RequestMapping(method = "GET", path ="/login")
    public void getLoginPage(HttpRequest request, HttpResponse response) throws IOException {
        response.sendHtml("/login/index.html");
    }

    @RequestMapping(method = "POST", path ="/login")
    public void postLoginRequest(HttpRequest request, HttpResponse response) throws IOException {
        logger.info("User Login - User : {}", request.getBodyParam("userID"));
    }

}
