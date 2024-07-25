package study.blog.bootBlog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//로그인, 회원 가입 경로로 접근하면 뷰 파일을 연결하는 컨트롤러 생성
@Controller
public class UserViewController {
    @GetMapping("/login")
    public String login() {
        return "oauthLogin";
        // '/login' 경로로 접근하면 login() 메소드가 login.html을 반환 
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }
}
