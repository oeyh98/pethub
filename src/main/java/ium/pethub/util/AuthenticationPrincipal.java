package ium.pethub.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // Annotation의 생성될 위치 - method의 파라미터에서만
@Retention(RetentionPolicy.RUNTIME) // Annotation 유지 정책 - Byte Code File까지 유지
//reflection: 구체적인 Class Type을 알지 못해도, 그 Class의 method, type, field들에 접근할 수 있도록 해주는 Java API
public @interface AuthenticationPrincipal {
}
