package ium.pethub.util;

import io.jsonwebtoken.*;
import ium.pethub.domain.entity.RoleType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

import static ium.pethub.util.AuthConstants.ACCESS_EXPIRE;
import static ium.pethub.util.AuthConstants.PASSWORD_RESET_TOKEN_VALID_TIME;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements InitializingBean {

    @Value("${secret.key}")
    private String SECRET_KEY;


    @Override
    public void afterPropertiesSet() throws Exception {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

    public String createPasswordResetToken() {
        Claims claims = Jwts.claims();

        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + PASSWORD_RESET_TOKEN_VALID_TIME)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)  // 사용할 암호화 알고리즘과
                .compact();
    }


    public String createAccessToken(Long userId, RoleType roleType) {
        Claims claims = Jwts.claims();//.setSubject(userPk); // JWT payload 에 저장되는 정보단위
        claims.put("userId", userId);
        claims.put("role", roleType);

        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + ACCESS_EXPIRE)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)  // 사용할 암호화 알고리즘과
                .compact();
    }


    public String createRefreshToken(Long userId) {
        Claims claims = Jwts.claims();
        claims.put("userId",userId);
        Date now = new Date();
        Date expiration = new Date(now.getTime() + ACCESS_EXPIRE);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }


    public Claims getClaims(String token){
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return claims;
    }

    public Long getUserId(String token){
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return Long.parseLong(String.valueOf(claims.get("userId")));
    }


}
