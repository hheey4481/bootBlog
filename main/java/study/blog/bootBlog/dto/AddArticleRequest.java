package study.blog.bootBlog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.blog.bootBlog.domain.Article;

@NoArgsConstructor  //기본 생성자 추가
@AllArgsConstructor //모든 필드 값을 파라미터로 받는 생성자 추가
@Getter
public class AddArticleRequest {

    private String title;
    private String content;


    public Article toEntity(String author) {
        //author 값도 추가 저장하도록
        
        //생성자를 이용해 객체 생성
        //빌더 패턴을 사용해 DTO를 엔티티로 만들어주는 메소드
        //이 메소드는 블로그 글을 추가할 때 저장할 엔티티로 변환하는 용도로 사용
        return Article.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }

}
