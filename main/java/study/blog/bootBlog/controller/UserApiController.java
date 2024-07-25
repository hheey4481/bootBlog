package study.blog.bootBlog.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import study.blog.bootBlog.dto.AddUserRequest;
import study.blog.bootBlog.service.UserService;

@RequiredArgsConstructor
@Controller
public class UserApiController {
    
    private final UserService userService;
    
    @PostMapping("/user")
    public String signup(AddUserRequest request) {
        userService.save(request);  //회원 가입 메소드 호출
        return "redirect:/login";   //회원 가입이 완료된 이후에 로그인 페이지로 이동
    }

    @GetMapping("/logout")
    //'/logout GET' 요청을 하면
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response,
                SecurityContextHolder.getContext().getAuthentication());
        //로그아웃 담당 핸들러의 logout() 메소드를 호출하여 로그아웃

        return "redirect:/login";
    }
}
