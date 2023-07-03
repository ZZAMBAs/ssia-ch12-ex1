package com.example.ssiach12ex1.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectConfig {
    @Value("${oauth2.clientId}")
    private String clientId;
    @Value("${oauth2.clientSecret}")
    private String clientSecret;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.oauth2Login(); // 인증 메서드 설정
        // oauth2Login()은 간단하게 필터 체인에 새 인증 필터(OAuth2LoginAuthenticationFilter)를 추가한다.
        /* 아래 clientRepository 메서드의 @Bean을 제거하고 아래처럼 인라인 구성 방식으로 적용할 수도 있다. 구성방식은 하나로 결정하는게 좋다.
        http.oauth2Login(c ->
                c.clientRegistrationRepository(clientRepository())
        );*/
        /* 이외에도 스프링 부트 구성 파일(application.yml)에서 직접 ClientRegistration과 ClientRegistrationRepository를 구성할 수 있다. 이를테면 깃헙일 경우,
         spring.security.oauth2.client.registration.github.client-id=~~, ... .github.client-secret=~~ 로 작성해줄 수 있다.
         이후에 단순히 http.oauth2Login(); 만 호출하면 끝.
         스프링 시큐리티에 알려지지 않은 공급자(이를테면 네이버)를 이용하려면 ... .client.provider로 시작하는 속성 그룹으로 권한 부여 서버의
         세부 정보도 기록해야 한다.
         */

        http.authorizeHttpRequests(a -> {
            a.anyRequest()
                    .authenticated();
        }); // 요청 시 인증 필요

        return http.build();
    }

    @Bean
    public ClientRegistrationRepository clientRepository(){
        var cr = clientRegistration();
        return new InMemoryClientRegistrationRepository(cr);
    }

    private ClientRegistration clientRegistration(){
        return CommonOAuth2Provider.GITHUB
                .getBuilder("github") // 클라이언트 등록을 위한 ID 제공
                .clientId(this.clientId)
                .clientSecret(this.clientSecret)
                .build();
    }

}
