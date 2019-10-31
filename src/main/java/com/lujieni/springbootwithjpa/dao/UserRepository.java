package com.lujieni.springbootwithjpa.dao;

import com.lujieni.springbootwithjpa.entity.pojo.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 *  加上@Repository注解Service层idea不会报错,对于程序而言没有影响
 *
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query(value = "select new com.lujieni.springbootwithjpa.entity.pojo.User(id,username,password) from User where password=?1")
    List<User> selectByPasswordWithPage(String password, Pageable pageable);
}
