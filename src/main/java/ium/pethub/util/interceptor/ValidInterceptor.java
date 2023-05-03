package ium.pethub.util.interceptor;

import ium.pethub.util.JwtTokenProvider;
import ium.pethub.util.UserContext;
import ium.pethub.util.ValidToken;
import ium.pethub.util.ValidTokenProcess;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@NoArgsConstructor
@ComponentScan
public class ValidInterceptor implements Ordered, HandlerInterceptor {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {

        HandlerMethod handlerMethod;

        if (!(handler instanceof HandlerMethod))
            return true;
        handlerMethod = (HandlerMethod) handler;

        ValidToken token = handlerMethod.getMethodAnnotation(ValidToken.class);
        if (token == null) {
            return true;
        }

        return ValidTokenProcess.execute(req,res,jwtTokenProvider);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        UserContext.remove();
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
