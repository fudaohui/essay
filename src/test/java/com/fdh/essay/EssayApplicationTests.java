package com.fdh.essay;

import com.fdh.essay.bean.Student;
import com.fdh.essay.dao.StudentDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EssayApplicationTests {


    @Autowired
    private StudentDao studentDao;

    @Autowired
    private StudentService studentService;

    @Test
    void contextLoads() {
    }


    @Test
    void testme() {

        int i = 10;

        for (; i < 100; i++) {
            Student student = new Student();
            student.setAge(i);
            student.setId((long) i);
            student.setName("lily" + i);
            int insert = studentDao.insert(student);
            if (insert == 1) {
                System.out.println("插入成功！");
            } else {
                System.out.println("插入失败！");
            }
            studentService.asyncTest((long) i);
        }

    }


}
