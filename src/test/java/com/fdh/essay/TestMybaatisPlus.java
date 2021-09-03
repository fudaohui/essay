package com.fdh.essay;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fdh.essay.sys.entity.Student;
import com.fdh.essay.sys.service.IStudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author: fudaohui
 * @date: 2021/09/02 19:43
 */
@SpringBootTest
public class TestMybaatisPlus {


    @Autowired
    private IStudentService iStudentService;
    @Test
    void testCount() {
        Page<Student> studentPage = new Page<>();
        studentPage.setSize(10);
        studentPage.setCurrent(0);
        IPage<Student> iPage = iStudentService.selectUserPage(studentPage, 20);
        List<Student> records = iPage.getRecords();
        for (Student record : records) {

            System.out.println(record.toString());
        }
    }
}
