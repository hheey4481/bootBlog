package study.blog.bootBlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.blog.bootBlog.domain.Article;

public interface BlogRepository extends JpaRepository<Article, Long> {
    //JPA를 통해 SQL쿼리를 작성하지 않고도 객체를 통해 데이터베이스를 조작할 수 있으며, 객체 지향적인 코드 작성과 유지 보수성 향상
    //기본적으로 Entity클래스를 작성한 후 Repository 인터페이스를 생성해야 함
    //Springboot에서 기본적인 작업을 도와주는 JpaRepository 인터페이스
}
