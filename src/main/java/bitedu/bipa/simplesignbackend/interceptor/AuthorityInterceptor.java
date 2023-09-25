package bitedu.bipa.simplesignbackend.interceptor;

import bitedu.bipa.simplesignbackend.mapper.AuthorityMapper;
import bitedu.bipa.simplesignbackend.model.dto.AuthorityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class AuthorityInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private AuthorityMapper authorityMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //handler 종류 확인 -> HandlerMethod 타입인지 체크
        if(handler instanceof HandlerMethod == false){
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        Authority authority = handlerMethod.getMethodAnnotation(Authority.class);

        //method에 @authority가 없으면 인증이 필요 X
        if(authority == null){
            return true;
        }

        //세션에서 userId
        HttpSession session = request.getSession();
        String userIdstr = (String) session.getAttribute("userId");

        if(userIdstr == null){
            response.setStatus(HttpStatus.UNAUTHORIZED.value()); // 401
            return false;
        }

        //DB에 해당 userId 권한 조회
        int userId = Integer.parseInt(userIdstr);
        AuthorityDTO authorityDTO = authorityMapper.findAuthority(userId);

        if(authorityDTO == null){
            response.setStatus(HttpStatus.FORBIDDEN.value()); //403
            return false;
        }

        // 권한 제크
        if(!authority.role().toString().equals(authorityDTO.getAuthorityName())){
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }

        return true;
    }
}
