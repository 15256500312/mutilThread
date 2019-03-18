package com.example.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import java.io.Serializable;
import java.sql.Date;


public class User  implements Serializable{
    private  int id;
    private String name;
    private  int age;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date birthDay;

    public User() {
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", birthDay=" + birthDay +
                '}';
    }
}
