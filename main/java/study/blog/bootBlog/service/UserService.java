package study.blog.bootBlog.service;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import study.blog.bootBlog.domain.User;
import study.blog.bootBlog.dto.AddUserRequest;
import study.blog.bootBlog.repository.UserRepository;

@RequiredArgsConstructor
@Service
// AddUserRequest 객체를 인수로 받는 회원 정보 추가 메소드
public class UserService {

    private final UserRepository userRepository;
 
    public Long save(AddUserRequest dto) {

        //패스워드 암호화
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return userRepository.save(User.builder()
                .email(dto.getEmail())
                //패스워드 암호화
                //패스워드를 저장할 때 시큐리티 설정,
                //패스워드 인코딩용으로 등록한 빈을 사용해서 암호화한 후 저장
                .password(encoder.encode(dto.getPassword()))
                .build()).getId();
    }
    
    //리프레시 토큰 생성에 이용
    //전달받은 유저id로 유저를 검색해서 전달하는 findById() 메소드
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    //이메일을 입력받아 users 테이블에서 유저 찾기
    //없으면 예외 발생
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }
}