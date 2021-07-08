package tacos.security;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // 사용자의 HTTP 요청 경로에 대해 접근 제한 등의 보안 처리를 우리가 원하는대로 지원하는 역할

    // HTTP 보안을 구성하는 클래스
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/designs", "/orders")
                .access("hasRole('ROLE_USER')")
                .antMatchers("/", "/**")
                .access("permitAll")
                .and()
                .httpBasic();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // 인메모리 기반 사용자 스토어 예제
        auth.inMemoryAuthentication()
                .withUser("user1") // 사용자 구성 시작
                .password("{noop}password1") // 비밀번호 부여
                .authorities("ROLE_USER") // 권한 부여
                .and()
                .withUser("user2")
                .password("{noop}password2")
                .authorities("ROLE_USER");
    }
}
