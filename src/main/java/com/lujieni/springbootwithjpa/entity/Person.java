package com.lujieni.springbootwithjpa.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Integer age;

    private String hobbyName;








}
