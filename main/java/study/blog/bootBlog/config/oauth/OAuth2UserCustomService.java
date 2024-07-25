package study.blog.bootBlog.config.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import study.blog.bootBlog.domain.User;
import study.blog.bootBlog.repository.UserRepository;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class OAuth2UserCustomService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;


    @Override
    //부모 클래스인 DefaultOAuth2UserService에서 제공하는 OAuth 서비스에서 제공하는 정보를 기반으로
    //loatUser() 메소드를 이용해 사용자 객체를 불러옴
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws
            OAuth2AuthenticationException {
        //요청을 바탕으로 유저 정보를 담은 객체 반환
        OAuth2User user = super.loadUser(userRequest);
        saveOrUpdate(user);
        return user;
    }

    private User saveOrUpdate(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        //사용자가 user 테이블에 있으면 업데이트하고,
        //없으면 사용자를 새로 생성해서 데이터베이스에 저장
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        User user = userRepository.findByEmail(email)
                .map(entity -> entity.update(name))
                .orElse(User.builder()
                        .email(email)
                        .nickname(name)
                        .build());
        return userRepository.save(user);
    }
}
