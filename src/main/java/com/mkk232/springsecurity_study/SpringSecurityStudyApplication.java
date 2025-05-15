package com.mkk232.springsecurity_study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

// Entity 클래스와 Repository 클래스가 Application 클래스와 다른 패키지에 있을 경우
//@EntityScan("package path")
//@EnableJpaRepositories("package path")
public class SpringSecurityStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityStudyApplication.class, args);
    }

}
