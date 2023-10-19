    package bitedu.bipa.simplesignbackend.interceptor;

    import bitedu.bipa.simplesignbackend.mapper.AuthorityMapper;
    import bitedu.bipa.simplesignbackend.model.dto.AuthorityDTO;
    import bitedu.bipa.simplesignbackend.model.dto.RoleRequestDTO;
    import bitedu.bipa.simplesignbackend.utils.SessionUtils;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.stereotype.Component;
    import org.springframework.web.method.HandlerMethod;
    import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    import java.util.Arrays;
    import java.util.Collections;
    import java.util.List;
    import java.util.Map;

    @Component
    public class AuthorityInterceptor extends HandlerInterceptorAdapter {
        @Autowired
        private AuthorityMapper authorityMapper;

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            int authorityCode = 3; //1은 master, 2는 부서관리자, 3은 일반사용자 필요에 따라 바꿔 사용하세요

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
            int userId = (int) SessionUtils.getAttribute("userId");
            if(userId == 0){
                response.setStatus(HttpStatus.UNAUTHORIZED.value()); // 401
                return false;
            }

            //DB에 해당 userId 권한 조회
            RoleRequestDTO roleRequestDTO = new RoleRequestDTO(userId, authorityCode);
            int roleCount = authorityMapper.findAuthority(roleRequestDTO);

            if(roleCount ==0) {
                throw  new RuntimeException(); //권한이 없습니다.
            }
            String authorityName = authorityMapper.getAuthorityName(authorityCode);

            // 권한 제크
            for(Authority.Role role: authority.role()) {
                if(role.toString().equals(authorityName)) {
                    return true;
                }
            }

           throw  new RuntimeException(); //권한이 없습니다.
        }
    }
