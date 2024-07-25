package study.blog.bootBlog.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import study.blog.bootBlog.domain.User;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
