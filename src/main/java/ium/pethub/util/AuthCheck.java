package ium.pethub.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AuthCheck {
    Role role();

    enum Role {
        USER(1),
        ADMIN(2),
        VET(3);


        private final int level;

        Role(int level) {
            this.level = level;
        }
    }
}
