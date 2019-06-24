package com.bnuz.novelreader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class NovelreaderApplication{
//extends SpringBootServletInitializer
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(NovelreaderApplication.class);
//    }

    public static void main(String[] args) {
        SpringApplication.run(NovelreaderApplication.class, args);
    }

}
