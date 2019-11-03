package com.lujieni.springbootwithjpa.dao;

import com.lujieni.springbootwithjpa.entity.pojo.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 *  加上@Repository注解Service层idea不会报错,对于程序而言没有影响
 *  继承JpaSpecificationExecutor接口可以让我们动态拼接sql
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {
/*
    @Query(value = "select id,username,password from User where password=?1",nativeQuery = true)
*/
    @Query(value = "select new com.lujieni.springbootwithjpa.entity.pojo.User(id,username,password) from User where password=?1")
    List<User> selectByPasswordWithPage(String password, Pageable pageable);
}
