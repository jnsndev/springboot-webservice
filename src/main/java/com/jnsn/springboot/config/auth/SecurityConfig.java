package com.jnsn.springboot.config.auth;

import com.jnsn.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// EnableWebSecurity: 스프링 시큐리티 설정들을 활성화 시켜준다.
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // h2-console 화면을 사용하기 위해 해당 옵션들을 disable 시킴
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    // URL별 권한 관리를 설정하는 옵션의 시작점
                    .authorizeRequests()
                    // 권한 관리 대상을 지정하는 옵션 / URL, HTTP 메서드별로 관리가 가능
                    // 지정된 URL들은 전체 열람 권한
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                    // "/api/v1/**" 주소를 가진 API는 USER 권한을 가진 사용자만 열람 권한
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                    // 설정된 값들 외의 나머지 URL / authenticated()를 통해 인증된 사용자(로그인한 사용자)에게만 허용
                    .anyRequest().authenticated()
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    // OAuth2 로그인 기능에 대한 여러 설정의 진입점
                    .oauth2Login()
                        // OAuth2 로그인 성공 후, 사용자 정보를 가져올 때의 설정을 담당
                        .userInfoEndpoint()
                            // 소셜 로그인 성공 시, 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록
                            .userService(customOAuth2UserService);
    }
}
