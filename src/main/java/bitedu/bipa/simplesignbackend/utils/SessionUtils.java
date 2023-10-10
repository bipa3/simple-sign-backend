package bitedu.bipa.simplesignbackend.utils;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class SessionUtils {
    public static void addAttribute(String name, Object value){
        RequestContextHolder.getRequestAttributes().setAttribute(name, value, RequestAttributes.SCOPE_SESSION);
    }

    public static void removeAttribute(String name){
        RequestContextHolder.getRequestAttributes().removeAttribute(name,RequestAttributes.SCOPE_SESSION);
    }

    public static Object getAttribute(String name){
        return RequestContextHolder.getRequestAttributes().getAttribute(name,RequestAttributes.SCOPE_SESSION);
    }
}
