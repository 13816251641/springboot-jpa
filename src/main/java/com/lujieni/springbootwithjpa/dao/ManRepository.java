package com.lujieni.springbootwithjpa.dao;

import com.lujieni.springbootwithjpa.entity.pojo.Man;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *  加上@Repository注解Service层idea不会报错,对于程序而言没有影响
 */
public interface ManRepository extends JpaRepository<Man,Integer> {

    List<Man> findByName(String name);

}
