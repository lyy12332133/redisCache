package com.huawei.domain;

import java.io.Serializable;

/**
 * Created by dllo on 17/11/27.
 */
public class Student implements Serializable{
    private int module_id;
    private String name;

    public Student(int module_id, String name) {
        this.module_id = module_id;
        this.name = name;
    }

    public Student() {
    }

    @Override
    public String toString() {
        return "Student{" +
                "module_id=" + module_id +
                ", name='" + name + '\'' +
                '}';
    }

    public int getModule_id() {
        return module_id;
    }

    public void setModule_id(int module_id) {
        this.module_id = module_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
