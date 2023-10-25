package bitedu.bipa.simplesignbackend.utils;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * 세션을 받아 올 때
 * 1. userId -> int userId = (int) SessionUtils.getAttribute("userId");
 * 2. userName -> String userName = (String) SessionUtils.getAttribute("userName");
 * */
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

    public static Boolean hasIdAttribute(String key, int id){
        int sessionValue = Integer.parseInt(RequestContextHolder.getRequestAttributes().getAttribute(key, RequestAttributes.SCOPE_SESSION).toString());
        Boolean result = sessionValue == id;
        System.out.println("sessionValue: " + sessionValue + " value: " + id + " result: " + result);
        return result;
    }
}
