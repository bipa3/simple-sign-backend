package bitedu.bipa.simplesignbackend.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Authority {

    //DB에 저장되어 있는 값에 따라 달라져야함
    public enum Role {MASTER_ADMIN, DEPT_ADMIN, USER}

    public Role role() default Role.USER;
}
