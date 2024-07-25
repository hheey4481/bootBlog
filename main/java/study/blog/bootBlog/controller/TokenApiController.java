package study.blog.bootBlog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import study.blog.bootBlog.dto.CreateAccessTokenRequest;
import study.blog.bootBlog.dto.CreateAccessTokenResponse;
import study.blog.bootBlog.service.TokenService;

@RequiredArgsConstructor
@RestController
public class TokenApiController {
    private final TokenService tokenService;

    //POST 요청이 오면
    @PostMapping("/api/token")
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken
            (@RequestBody CreateAccessTokenRequest request) {
        //tokenService에서 리프레시 토큰을 기반으로 새로운 엑세스 토큰을 생성
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateAccessTokenResponse(newAccessToken));
    }
}
