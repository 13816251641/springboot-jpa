package com.lujieni.springbootwithjpa.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 一对一示例
 */
@Data
@Entity
public class Man implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String sex;

    @OneToOne(fetch=FetchType.EAGER,cascade = CascadeType.ALL) //JPA注释： 一对一关系
    // name:本表外键字段 referencedColumnName:本表外键字段对应的其它表的主键字段名称
    @JoinColumn(name="pet_id",referencedColumnName="id",nullable=false)
    private Pet pet;

}