package com.mkk232.springsecurity_study.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoansController {

    @GetMapping("/myLoans")
    public String getLoans() {
        return "Here are the loan details from the DB";
    }
}
