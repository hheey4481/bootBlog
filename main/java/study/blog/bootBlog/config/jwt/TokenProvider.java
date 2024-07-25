package study.blog.bootBlog.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import study.blog.bootBlog.domain.User;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TokenProvider {

    private final JwtProperties jwtProperties;


    public String generateToken(User user, Duration expiredAt) {
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

    //JWT 토큰 생성 메소드
    private String makeToken(Date expiry, User user) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)   //헤더 type: JWT
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)   //내용 iat(발급 시간): 현재 시간
                .setExpiration(expiry)  //내용 exp: expiry 변숫값
                .setSubject(user.getEmail())    //내용 sub: 유저의 이메일
                .claim("id", user.getId())  //클레임 id: user id
                //서명: 비밀값과 함께 해시값을 HS256 방식으로 암호화
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    //JWT 토큰 유효성 검증 메소드
    //JwtProperties.java에 선언한 비밀값과 함께 토큰 복호화
    public boolean validToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey())    //비밀값으로 복호화
                    .parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            //복호화 과정에서 에러가 발생하면 유효하지 않은 토큰이므로 false 반환
            return false;
        }
    }

    //토큰 기반으로 인증 정보를 가져오는 메소드
    //토큰을 받아 인증 정보를 담은 객체 Authentication을 반환하는 메소드
    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        //getClaims()를 호출해서 클레임 정보를 반환받아
        //사용자 이메일이 들어있는 토큰 제목 sub와 토큰 기반으로 인증 정보 생성
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities =
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails.User(claims.getSubject(),
                //User 임포트할 때 만든 클래스가 아닌 스프링 시큐리티에서 제공하는 객체인 User 클래스 import
                "", authorities), token, authorities);
    }

    //토큰 기반으로 유저 ID를 가져오는 메소드
    public Long getUserId(String token) {
        //getClaims()를 호출해 클레임 정보를 반환 받고
        Claims claims = getClaims(token);
        //클레임에서 id 키로 저장된 값을 가져와 반환
        return claims.get("id", Long.class);
    }

    //프로퍼티즈 파일에 저장한 비밀값으로 토큰을 복호화한 뒤 클레임을 가져오는 private 메소드
    private Claims getClaims(String token) {
        return Jwts.parser()    //클레임 조회
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }


}
