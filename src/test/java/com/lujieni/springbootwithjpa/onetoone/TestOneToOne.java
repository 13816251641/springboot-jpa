package com.lujieni.springbootwithjpa.onetoone;


import com.lujieni.springbootwithjpa.dao.ManRepository;
import com.lujieni.springbootwithjpa.entity.Man;
import com.lujieni.springbootwithjpa.entity.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 一对一表关系测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestOneToOne {

    @Autowired
    private ManRepository manRepository;


    /**
     * 级联删除没问题
     */
    @Test
    public void oneToOneDelete(){
        manRepository.deleteById(2);
    }

    /**
     * 测试一对一查询,jpa还是查询了2次
     */
    @Test
    public void oneToOneSearch(){
        List<Man> list = manRepository.findByName("张三");
        System.out.println(list);
    }


}
