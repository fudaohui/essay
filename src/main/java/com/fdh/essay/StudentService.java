package com.fdh.essay;

import com.fdh.essay.bean.Student;
import com.fdh.essay.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentDao studentDao;

    @Async
    public void asyncTest(Long id) {
        Student student = studentDao.selectByPrimaryKey(id);
        System.out.println("查询结果：" + student.toString());
    }


}
