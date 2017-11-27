package com.huawei.mapper;

import com.huawei.domain.Student;

import java.util.List;

/**
 * Created by dllo on 17/11/27.
 */
public interface StudentMapper {

    List<Student> findAllStudent();
}
