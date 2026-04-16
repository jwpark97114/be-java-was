package interfaces;

import jhttp.HttpRequest;
import jhttp.HttpResponse;
import webserver.session.SessionManager;

import java.io.InvalidClassException;
import java.lang.reflect.InvocationTargetException;

@FunctionalInterface
public interface HandlerMethod {
    public void handle(HttpRequest request, HttpResponse response, SessionManager sessionManager) throws InvocationTargetException, IllegalAccessException, InvalidClassException;
}
