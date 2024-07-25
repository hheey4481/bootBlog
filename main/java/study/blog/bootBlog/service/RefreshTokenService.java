package study.blog.bootBlog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.blog.bootBlog.domain.RefreshToken;
import study.blog.bootBlog.repository.RefreshTokenRepository;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    //전달받은 리프레시 토큰으로 리프레시 토큰 객체를 검색해서 전달하는 
    //findByRefreshToken() 메소드 구현
    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected token"));
    }
}
