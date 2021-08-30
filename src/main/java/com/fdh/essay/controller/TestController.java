package com.fdh.essay.controller;

import com.fdh.essay.distruptor.TestProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: fudaohui
 * @date: 2021/08/30 18:37
 */
@RestController
public class TestController {
    @Autowired
    private TestProcessor testProcessor;

    @GetMapping("/aa")
    public String aa() {
        for (int l = 0; true; l++) {

            testProcessor.publishEvent(l++);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
