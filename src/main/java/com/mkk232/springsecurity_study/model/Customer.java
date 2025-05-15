package com.mkk232.springsecurity_study.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customer") // 참고를 위해 작성
@Getter @Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String pwd;
    private String role;

//    @Column(name = "role") // DB컬럼과 필드명이 다를 경우 사용


}
