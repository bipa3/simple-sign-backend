    package bitedu.bipa.simplesignbackend.interceptor;
;
    import org.springframework.stereotype.Component;
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
//            String cookieHeader = response.getHeader("Set-Cookie");
//            if (cookieHeader != null && cookieHeader.startsWith("JSESSIONID")) {
//                cookieHeader = cookieHeader.concat("; Secure; SameSite=None");
//                response.setHeader("Set-Cookie", cookieHeader);
//            }

            response.setHeader("Set-Cookie", "JSESSIONID=" + request.getRequestedSessionId() + "; path=/; Secure; SameSite=None");
        }
    }
