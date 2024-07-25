//package study.blog.bootBlog.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import study.blog.bootBlog.service.UserDetailService;
//
//@RequiredArgsConstructor
//@Configuration
//public class WebSecurityConfig {
//
//    private final UserDetailsService userService;
//
//    //스프링 시큐리티 기능 비활성화
//    @Bean
//    public WebSecurityCustomizer configure() {
//        return (web) -> web.ignoring()
//                .requestMatchers("/static/**");
//                //.requestMatchers() : 특정 요청과 일치하는 url에 대한 엑세스 설정
//    }
//
//    //특정 http 요청에 대한 웹 기반 보안 구성
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((authorizeRequests) ->
//                        authorizeRequests
//                                .requestMatchers("/login").permitAll()
//                                .requestMatchers("/signup").permitAll()
//                                .requestMatchers("/user").permitAll()
//                                .anyRequest().authenticated()
//                )    //인증, 인가 설정
//                .formLogin((formLogin) ->
//                        formLogin
//                                .loginPage("/login")
//                                .defaultSuccessUrl("/articles"))    //폼 기반 로그인 설정
//
//                .logout((logoutConfig) ->
//                        logoutConfig.logoutSuccessUrl("/login")
//                                .invalidateHttpSession(true))   //로그아웃 설정
//
//                .csrf((csrfConfig) ->
//                        csrfConfig.disable());   //csrf 비활성화
//
//                return http.build();
//    }
//
//    //인증 관리자 관련 설정
//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//
//        daoAuthenticationProvider.setUserDetailsService(userService);
//        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
//
//        return daoAuthenticationProvider;
//    }
//
//    //패스워드 인코더로 사용할 빈 등록
//    @Bean
//    public  BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
