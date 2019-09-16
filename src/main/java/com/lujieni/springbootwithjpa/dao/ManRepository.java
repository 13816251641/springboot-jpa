package com.lujieni.springbootwithjpa.dao;

import com.lujieni.springbootwithjpa.entity.Man;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 这里什么注解都不用配置,好神奇
 */
public interface ManRepository extends JpaRepository<Man,Integer> {

    List<Man> findByName(String name);

}
