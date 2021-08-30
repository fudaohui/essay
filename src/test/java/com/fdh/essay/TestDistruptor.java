package com.fdh.essay;

import com.fdh.essay.bean.Student;
import com.fdh.essay.dao.StudentDao;
import com.fdh.essay.distruptor.TestProcessor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestDistruptor {


    @Autowired
    private TestProcessor testProcessor;


    @Test
    void testme() {

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
