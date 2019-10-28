package com.lujieni.springbootwithjpa.dao;

import com.lujieni.springbootwithjpa.entity.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


/**
 *  加上@Repository注解Service层idea不会报错,对于程序而言没有影响
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

}
