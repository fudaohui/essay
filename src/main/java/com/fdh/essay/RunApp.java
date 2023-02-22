package com.fdh.essay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBootApplication注解包含@HasInherited
 */
@SpringBootApplication
public class RunApp extends EssayApplication{
    public static void main(String[] args) {
        SpringApplication.run(RunApp.class);
    }
}
