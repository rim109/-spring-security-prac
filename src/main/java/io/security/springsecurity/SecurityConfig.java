package io.security.springsecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/loginPage")
                        .loginProcessingUrl("/loginProc")
                        .defaultSuccessUrl("/",false)
                        .failureUrl("/failed")
                        .usernameParameter("userId")
                        .passwordParameter("passwd")
                        .successHandler((request, response, authentication) ->  {
                                System.out.println("authentication : " + authentication);
                                response.sendRedirect("/home");
                        })
                        .failureHandler((request, response, exception) -> {
                            System.out.println("exception : " + exception.getMessage());
                            response.sendRedirect("/login");
                        })
                        .permitAll()
                );

        return http.build();
    }
}
// Customizer.withDefaults()는 기본 설정이다.
// 보통은 YML 파일에 name과 password를 작성하여 하는 방법도 있지만, UserDetailsService 로 해서 인메모리에 직접 저장하여 설정해줄수 있으며 여러 개도 설정 가능하다.
// alwaysUse로 true로 설정해주면 어떤 링크로 들어가도 로그인 하게 되면 설정된 url로 들어가고 false로 하면 링크로 들어간걸로 로그인 되면 들어가진다.
// 인증 전에 보안이 필요한 페이지를 방문하다가 인증에 성공한 경우이면 이전 위치로 리다이렉트 된다.
