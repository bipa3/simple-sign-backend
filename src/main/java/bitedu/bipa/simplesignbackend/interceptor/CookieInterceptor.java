    package bitedu.bipa.simplesignbackend.interceptor;
;
    import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;

    @Component
    public class CookieInterceptor extends HandlerInterceptorAdapter {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

            //response.setHeader("Set-Cookie", "JSESSIONID=" + request.getRequestedSessionId() + "; path=/; Secure; SameSite=None");

            return true;
        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
            System.out.println(RequestContextHolder.getRequestAttributes().getSessionId());
            response.setHeader("Set-Cookie", "JSESSIONID=" + RequestContextHolder.getRequestAttributes().getSessionId() + "; path=/; Secure; SameSite=None");
        }
    }
