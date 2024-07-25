package study.blog.bootBlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.blog.bootBlog.domain.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUserId(Long userId);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
