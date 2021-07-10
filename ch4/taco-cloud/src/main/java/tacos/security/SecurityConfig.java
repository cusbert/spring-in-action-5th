package tacos.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // 사용자의 HTTP 요청 경로에 대해 접근 제한 등의 보안 처리를 우리가 원하는대로 지원하는 역할

    @Autowired
    DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // HTTP 보안을 구성하는 클래스
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/design", "/orders") // ROLE_USER 권한이 있는 유저만 허용
                .access("hasRole('ROLE_USER')")
                .antMatchers("/", "/**")
                .access("permitAll") // 이 외의 요청은 모두 허용
                .and() // 인증 구성이 끝나서 http 구성을 적용할 준비가 되었다
                    .formLogin() // 커스텀 로그인 폼을 구성하기 위해 호출
                    .loginPage("/login")
                    // .loginProcessingUrl("/authenticate") // /authenticate 경로의 로그인 처리
                    .defaultSuccessUrl("/designs") // 로그인 페이지로 이동한 후 로그인 성공시 /design 페이지로 이동
                    //.defaultSuccessUrl("/designs", true) // 로그인 전에 어떤 페이지에 있었던 로그인 성공시 /design 페이지로 이동
                    //.usernameParameter("user")
                    //.passwordParameter("pwd")
                .and()
                    .logout()
                    .logoutSuccessUrl("/")
                .and()
                    .csrf();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // 사용자 인증 커스터마이징
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());

        /* LDAP 기반 사용자 스토어
        auth.ldapAuthentication()
                .userSearchBase("ou=people")
                .userSearchFilter("(uid={0})")
                .groupSearchBase("ou=groups")
                .groupSearchFilter("(member={0})")
                .contextSource()
                .root("dc=tacocloud,dc=com")
                .ldif("classpath:users.ldif")
                .and()
                .passwordCompare()
                .passwordEncoder(new BCryptPasswordEncoder())
                .passwordAttribute("userPasscode");
        */

        /* JDBC 기반의 사용자 스토어
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username, password, enabled from users " +
                                "where username=?")
                .authoritiesByUsernameQuery(
                        "select username, authority from authorities " +
                                "where username=?")
                .passwordEncoder(new NoEncodingPasswordEncoder());
                // .passwordEncoder(new BCryptPasswordEncoder());
        */

        /* 인메모리 기반 사용자 스토어 예제
        auth.inMemoryAuthentication()
                .withUser("user1") // 사용자 구성 시작udxo20
                .password("{noop}password1") // 비밀번호 부여
                .authorities("ROLE_USER") // 권한 부여
                .and()
                .withUser("user2")
                .password("{noop}password2")
                .authorities("ROLE_USER");
         */
    }
}
