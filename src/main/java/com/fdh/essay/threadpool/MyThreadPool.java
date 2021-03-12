package com.fdh.essay.threadpool;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class MyThreadPool {




    @Bean("aaThreadPool")
    public ExecutorService build() {
        return Executors.newSingleThreadExecutor();
    }


}
