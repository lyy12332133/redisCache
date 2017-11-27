package com.huawei.controller;

import com.huawei.domain.Student;
import com.huawei.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dllo on 17/11/27.
 */
@Controller
public class MainController {

    @Resource
    private StudentService studentService;
    @RequestMapping(value = "/home")
    public String frontPage(){
        return "home";
    }

    //JSON/XML

    @ResponseBody
    @RequestMapping("/getall")
    public Object jsonData(){
        List<Student> studentList = studentService.findAllStudent();
        return studentList;
    }
}
