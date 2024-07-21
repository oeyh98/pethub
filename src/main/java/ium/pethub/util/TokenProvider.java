package ium.pethub.util;

import io.jsonwebtoken.*;
import ium.pethub.domain.entity.RoleType;
import ium.pethub.domain.entity.User;
import ium.pethub.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Base64;

import jakarta.annotation.PostConstruct;
import java.util.Date;

import static ium.pethub.util.AuthConstants.ACCESS_EXPIRE;
import static ium.pethub.util.AuthConstants.REFRESH_EXPIRE;

@Component
@Slf4j
@RequiredArgsConstructor
public class TokenProvider {

    private final UserRepository userRepository;

    @Value("${secret.key}")
    private String SECRET_KEY;

    @PostConstruct
    protected void init() {
        SECRET_KEY = Base64.getUrlEncoder().encodeToString(SECRET_KEY.getBytes());
    }
    //권한정보를 이용해서 토큰을 생성하는 메소드
    public String createAccessToken(Long userId, RoleType role) {

        Claims claims = Jwts.claims(); // JWT payload 에 저장되는 정보단위
        claims.put("userId", userId);
        claims.put("role", role); // 정보는 key / value 쌍으로 저장된다.
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + ACCESS_EXPIRE)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();
    }

    public String createRefreshToken(Long userId) {

        Claims claims = Jwts.claims();
        claims.put("userId", userId);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_EXPIRE))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }


    public Long decode(String token) {
        Claims claims = getClaimsToken(token);
        Long userId = ((Number) claims.get("userId")).longValue();

        return userId;
    }

    public Claims getClaimsToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
//    public boolean hasRefreshToken(Long userId) {
//        User user = userRepository.findById(userId).get();
//        String refreshToken = user.getRefreshToken();
//        if (refreshToken == null) {
//            return false;
//        }
//        return isValidRefreshToken(refreshToken);
//    }
//    public boolean isValidAccessToken(String token) {
//        try {
//            Claims claims = getClaimsToken(token);
//
//            log.info("expireTime: " + claims.getExpiration());
//            log.info("userId: " + claims.get("userId"));
//            log.info("role: " + claims.get("role"));
//            return true;
//        } catch (SecurityException | MalformedJwtException e) {
//            log.info("잘못된 JWT 서명입니다.");
//            return false;
//        } catch (ExpiredJwtException e) {
//            log.info("만료된 JWT 토큰입니다.");
//            return false;
//        } catch (UnsupportedJwtException e) {
//            log.info("지원되지 않는 JWT 토큰입니다.");
//            return false;
//        } catch (IllegalArgumentException e) {
//            log.info("JWT 토큰이 잘못되었습니다.");
//            return false;
//        } catch (NullPointerException exception) {
//            log.info("Token is null");
//            return false;
//        }
//    }
//
//    public boolean isValidRefreshToken(String token) {
//        try {
//            Claims claims = getClaimsToken(token);
//            log.info("expireTime: " + claims.getExpiration());
//            log.info("userId: " + claims.get("userId"));
//            return true;
//        } catch (SecurityException | MalformedJwtException e) {
//            log.info("잘못된 JWT 서명입니다.");
//        } catch (ExpiredJwtException e) {
//            log.info("만료된 JWT refresh 토큰입니다.");
//        } catch (UnsupportedJwtException e) {
//            log.info("지원되지 않는 JWT 토큰입니다.");
//        } catch (IllegalArgumentException e) {
//            log.info("JWT 토큰이 잘못되었습니다.");
//        } catch (NullPointerException exception) {
//            log.info("Token is null");
//        }
//        return false;
//    }
}