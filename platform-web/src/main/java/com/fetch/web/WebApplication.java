package com.fetch.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class WebApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(WebApplication.class, args);
        //debug(ctx);
    }

    private static void debug(ApplicationContext ctx) {
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println("!!!!!!!!!!" + beanName);
        }
    }

}
