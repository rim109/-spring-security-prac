package io.security.springsecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .httpBasic(basic -> basic.authenticationEntryPoint(new CustomAuthenticationEntryPoint()));

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user = User.withUsername("user").password("{noop}1111").roles("USER").build();
        return new InMemoryUserDetailsManager(user);
    }
}
// Customizer.withDefaults()는 기본 설정이다.
// 보통은 YML 파일에 name과 password를 작성하여 하는 방법도 있지만, UserDetailsService 로 해서 인메모리에 직접 저장하여 설정해줄수 있으며 여러 개도 설정 가능하다.
// 서버를 실행하면 하나의 로그인 창이 뜨는데 아무런 입력을 안했을 시 401 에러 메시지가 뜨면서 헤더에 Www-Authenticate Basic realm="Realm" 이라는 것이 뜨고
// 로그인에 알맞은 유저와 비밀번호를 입력할 시 200 Ok 가 뜨면서 이전에 헤더에 떴던 Www-Authenticate가 뜨지 않는다.
