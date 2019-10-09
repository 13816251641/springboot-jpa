package com.lujieni.springbootwithjpa.entity.bo;


import lombok.Data;

@Data
public class PersonBO {
    private String name;
    private Long age;

    public PersonBO(String name, Long age) {
        this.name = name;
        this.age = age;
    }


}
