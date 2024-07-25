package study.blog.bootBlog.config.jwt;

import io.jsonwebtoken.io.Encoders;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Setter
@Getter
@Component
@ConfigurationProperties("jwt") //자바 클래스에 프로퍼티값을 가져와서 사용하는 애너테이션
public class JwtProperties {
    
    //해당 값들을 변수로 접근하는데 사용하는 클래스임

    private String issuer;


    private String secretKey;


    public String getSecretKey() {
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }

}
