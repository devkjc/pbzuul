package com.toy.pbzuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class PbzuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(PbzuulApplication.class, args);
    }

}
