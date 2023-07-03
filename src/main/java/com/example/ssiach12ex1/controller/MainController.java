package com.example.ssiach12ex1.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class MainController {
    @GetMapping("/")
    public String main(OAuth2AuthenticationToken token){ // 스프링에서 기본 지원하는 Authentication 구현체 OAuth2AuthenticationToken
        // 스프링 부트는 사용자를 나타내는 Authentication 객체를 자동으로 메서드의 매개 변수에 주입한다.

        log.info(token.getPrincipal().toString());
        return "main.html";
    }
}
