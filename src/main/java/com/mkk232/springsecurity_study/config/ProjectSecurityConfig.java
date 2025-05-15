package com.mkk232.springsecurity_study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurityConfig {

    /**
     * SecurityFilterChain : Spring Security의 필터 체인 설정
     */
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        // authenticated(): 인증된 사용자만 접근 가능
        // permitAll() : 모든 요청을 허용
        // anyRequest().permitAll() : 같이 사용하지 말자. spring security를 사용하는 의미가 없어짐.
        // http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());

        // denyAll() : 인증된 사용자든 아니든 모든 요청을 거부, 403 에러 반환
        // http.authorizeHttpRequests((requests) -> requests.anyRequest().denyAll());

        // requestMatchers() : 특정 요청에 대해 인증을 요구
        http
                .csrf(csrfConfig -> csrfConfig.disable())
                .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/myAccount", "/myBalance", "/myLoans", "myCards").authenticated()
                .requestMatchers("/notices", "/contact", "/error", "/register").permitAll()); // error: Spring Security에서 제공하는 기본 에러 페이지

        // http.formLogin(flc -> flc.disable()); // 로그인 폼 비활성화, UsernamePasswordAuthenticationFilter.attemptAuthentication() 의 인증 절차가 비활성화됨
        // http.httpBasic(hdc -> hdc.disable()); // HTTP Basic 인증 비활성화, BasicAuthenticationFilter.doFilterInternal() 의 인증 절차가 비활성화됨

        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());

        return http.build();
    }

    /**
     * 개발용으로 사용하는 메모리 기반 사용자 저장소
     * 추후 데이터베이스에 연결하여 사용자 정보를 가져오는 방식으로 변경할 예정
     *
     * UserDetailsService vs UserDetailsManager
     *   - UserDetailsService : 사용자 정보를 가져오는 인터페이스 (사용자 인증 시 사용)
     *   - UserDetailsManager : 사용자 정보를 추가, 수정, 삭제하는 인터페이스
     */
/*    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        // {noop}: Spring Security에서 비밀번호를 암호화하지 않도록 설정 (PasswordEncoder를 사용하여 비밀번호 암호화 권장)
//        UserDetails user = User.withUsername("user").password("{noop}mkk232@12345").authorities("read").build();
//        UserDetails admin = User.withUsername("admin").password("{bcrypt}$2a$12$GxciGjNq640NC7Dj9bUIUe/YrGhp6VN6htGiUEjodGrRxhTSfase.").authorities("admin").build();

//        return new InMemoryUserDetailsManager(user, admin);
        // DataBase로 변경하여 위 코드는 주석처리

        return new JdbcUserDetailsManager(dataSource);
    }*/


    /**
     * PasswordEncoder : 비밀번호 암호화 방식 설정
     * BCryptPasswordEncoder : Spring Security에서 제공하는 비밀번호 암호화 방식
     * PasswordEncoderFactories.createDelegatingPasswordEncoder() : Spring Security에서 권장하는 비밀번호 암호화 방식(BCryptPasswordEncoder)을 사용하기 위해 사용
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * CompromisedPasswordChecker : 비밀번호가 유출된 적이 있는지 확인하는 기능을 제공 (간단한 비밀번호 여부 체크)
     * 로그인 / 회원가입 시 간단한 비밀번호 사용을 막기위해 활용
     * 실제 운용환경에서 사용 권장
     */
    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }

    /**
     * UserDetailsManager -> InMemoryUserDetailsManager, JdbcUserDetailsManager, LDAPUserDetailsManager 메소드를 구현함.
     * 위 메소드 중 필요한 메소드가 없다면 커스터마이징을 통해 구현 가능.
     */

    /**
     * UserDetails vs Authentication
     *   - UserDetails : 사용자 정보를 담는 인터페이스
     *   - Authentication : 사용자 인증 정보를 담는 인터페이스
     *
     * UserDetails는 사용자 정보를 담는 인터페이스
     * 사용자 명, 비밀번호, 권한, 계정 만료 여부, 비밀번호 만료 여부, 계정 잠금 여부, 비밀번호 잠금 여부 등을 담고 있음
     * UserDetails 인터페이스를 구현한 User 클래스가 있음.
     *
     * Authentication은 사용자 인증 정보를 담는 인터페이스
     * 인증된 사용자 정보, 인증 방법, 인증 토큰 등을 담고 있음
     * Authentication 인터페이스를 구현한 UsernamePasswordAuthenticationToken 클래스가 있음.
     */
}
