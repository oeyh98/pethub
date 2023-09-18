package ium.pethub.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ium.pethub.util.AuthConstants.ACCESS_TOKEN;
import static ium.pethub.util.AuthConstants.REFRESH_TOKEN;

public class ValidTokenProcess {

    public static boolean execute(HttpServletRequest req, HttpServletResponse res, TokenProvider TokenProvider) throws IOException {
        String accessToken = new String();
        String refreshToken = new String();

        Cookie[] cookies = req.getCookies();
        if (cookies != null) { // 쿠키가 존재한다면
            for (Cookie cookie : cookies) {
                switch (cookie.getName()) {
                    case ACCESS_TOKEN:
                        accessToken = cookie.getValue();
                        break;
                    case REFRESH_TOKEN:
                        refreshToken = cookie.getValue();
                }
            }
        }

        if (!refreshToken.equals("")) { // 리프레시 토큰이 있다면
            try {
                Claims claims = TokenProvider.getClaims(refreshToken);
                UserContext.userData.set(new TokenUserData(claims));
            } catch (ExpiredJwtException exception) {
                res.sendRedirect("/api/auth/login");
                return false;
            }

        } else if (!accessToken.equals("")) {
            try {
                Claims claims = TokenProvider.getClaims(accessToken);
                UserContext.userData.set(new TokenUserData(claims));
            } catch (ExpiredJwtException exception) {
                res.setStatus(401);  // 에러 만들기
                return false;
            }
        }
        return true;
    }
}
