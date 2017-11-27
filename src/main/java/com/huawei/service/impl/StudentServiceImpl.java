package com.huawei.service.impl;

import com.huawei.domain.Student;
import com.huawei.mapper.StudentMapper;
import com.huawei.service.StudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by dllo on 17/11/27.
 */
@Service
public class StudentServiceImpl implements StudentService{
    @Resource
    private StudentMapper studentMapper;

    @Override
    public List<Student> findAllStudent() {
        return studentMapper.findAllStudent();
    }
}
