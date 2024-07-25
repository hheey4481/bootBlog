package study.blog.bootBlog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.blog.bootBlog.config.jwt.TokenProvider;
import study.blog.bootBlog.domain.User;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    //전달받은 리프레시 토큰으로 토큰 유효성 검사를 진행하고,
    //유효한 토큰일 때 리프레시 토큰으로 사용자id를 찾음
    public String createNewAccessToken(String refreshToken) {
        //토큰 유효성 검사에 실패하면 예외 발생
        if (!tokenProvider.validToken((refreshToken))) {
            throw new IllegalArgumentException("Unexpected token");
        }

        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        User user = userService.findById(userId);

        //토큰 제공자의 generateToken() 메소드 호출
        //새로운 엑세스 토큰을 생성
        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }
}
