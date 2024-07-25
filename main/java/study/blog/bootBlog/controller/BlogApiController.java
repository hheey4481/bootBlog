package study.blog.bootBlog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.blog.bootBlog.domain.Article;
import study.blog.bootBlog.dto.AddArticleRequest;
import study.blog.bootBlog.dto.ArticleResponse;
import study.blog.bootBlog.dto.UpdateArticleRequest;
import study.blog.bootBlog.service.BlogService;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController //HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
public class BlogApiController {

    private final BlogService blogService;

    //HTTP 메서드가 POST일 때 전달받은 URL과 동일하면 메소드로 매핑
    @PostMapping("/api/articles")
    //@RequestBody로 요청 본문 값 매핑
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request, Principal principal) {
        //Principal은 자바의 표준 시큐리티 기술로, 로그인이 된 상태라면 계정 정보를 담고 있음
        //principal 객체를 파라미터로 추가
        
        //인증 객체에서 유저 이름을 가져온 뒤, save()메소드로 넘겨줌
        Article savedArticle = blogService.save(request, principal.getName());

        //요청한 자원이 성공적으로 생성되었으면 저장된 블로그 글 정보를 응답객체에 담아 전송
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
        //ResponseEntity.status().body()는 응답 코드 - Created를 응답하고 테이블에 저장된 객체 반환
    }
    
    // /api/articles GET 요청이 오면 글 목록을 조회할 메소드
    // 전체 글을 조회한 뒤 반환
    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        List<ArticleResponse> articles = blogService.findAll()
                ///api/articles GET 요청이 오면, 글 전체를 조회하는 findAll() 메소드 호출 후,
                .stream()
                //stream()은 여러 데이터가 모여있는 컬렉션을 간편하게 처리하기 위한 기능
                .map(ArticleResponse::new)
                //응답용 객체인 ArticleResponse로 파싱해서
                .toList();

        return ResponseEntity.ok()
                .body(articles);
                //바디에 담아 클라이언트에 전송
    }

    //GET 요청이 오면 블로그 글을 조회하기 위해 매핑할 findArticle() 메소드 작성
    @GetMapping("/api/articles/{id}")
    //URL 경로에서 값 추출
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id) {
        //@PathVariable: URL에서 값을 가져오는 애너테이션
        //url에서 GET 요청을 받으면 값이 id로 들어옴
        //그리고 이 값이 BlogService의 findById() 메소드로 넘어가 블로그 글을 찾음
        //글을 찾아서 글의 정보를 body에 담아 웹 브라우저로 전송
        Article article = blogService.findById(id);

        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    //DELETE 요청이 오면 글을 삭제하기 위한 findArticles() 메소드
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id) {
        blogService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    //PUT 요청이 오면 글을 수정하기 위한 updateArticle() 메소드
    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable long id,
                                                 @RequestBody UpdateArticleRequest request) {
                                                //@RequestBody: 데이터를 인자에 할당하는 대표적인 방법
        Article updatedArticle = blogService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedArticle);

    }
}
