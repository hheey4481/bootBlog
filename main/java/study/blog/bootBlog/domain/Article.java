package study.blog.bootBlog.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity //엔티티로 지정, 클래스와 이름이 같은 테이블과 매핑
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  //파라미터가 없는 디폴트 생성자를 생성
public class Article {
    @Id //id 필드를 기본키로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)     //기본 키를 자동으로 1씩 증가
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)   //title이라는 not null 컬럼과 매핑
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "author", nullable = false)
    private String author;

    @Builder    //빌더 패턴으로 객체 생성
    public Article(String title, String content, String author) {
        this.author = author;
        this.title = title;
        this.content = content;
    }

    //엔티티에 요청받은 내용으로 값을 수정하는 메소드
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @CreatedDate    //엔티티가 생성될 때 생성 시간을
    @Column(name = "created_at")    //create_at 컬럼에 저장
    private LocalDateTime createdAt;

    @LastModifiedDate   //엔티티가 수정될 때 수정 시간 저장
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
