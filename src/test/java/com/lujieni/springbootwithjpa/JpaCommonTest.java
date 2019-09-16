package com.lujieni.springbootwithjpa;

import com.lujieni.springbootwithjpa.dao.PersonRepository;
import com.lujieni.springbootwithjpa.entity.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用jpa操作代码测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaCommonTest {

    @Autowired
    private PersonRepository personRepository;

    /**
     * 测试自定义sql查询
     */
    @Test
    public void ownSqlQuery(){
        List<Person> result = personRepository.findAllByHobbyName("睡觉");
        System.out.println(result);
    }

    /**
     * 根据name进行全模糊查询,jpa会自动帮你在字段左边加入%
     */
    @Test
    public void queryByNameEndingWith(){
        List<Person> result = personRepository.findByNameEndingWith("柳");
        System.out.println(result.size());
    }

    /**
     * 根据name进行全模糊查询,入参一定要带%,jpa不会帮你自动带入
     */
    @Test
    public void queryByNameLike(){
        List<Person> result = personRepository.findByNameLike("%赵%");
        System.out.println(result.size());
    }


    /**
     * 测试批量插入功能,发现jpa仍旧是一条条插入
     * Hibernate: insert into person (age, name) values (?, ?)
     * Hibernate: insert into person (age, name) values (?, ?)
     */
    @Test
    public void insertAll(){
        List<Person> list = new ArrayList<>();
        Person p1 = new Person();
        p1.setName("a");
        p1.setAge(18);
        Person p2 = new Person();
        p2.setName("b");
        p2.setAge(38);
        list.add(p1);
        list.add(p2);
        personRepository.saveAll(list);
    }


    /**
     * 测试单条插入功能
     */
    @Test
    public void insertOne() {
        Person p = new Person();
        p.setName("陆捷旎232");
        p.setAge(28);
        personRepository.save(p);
    }


    /**
     * 根据name值进行查询,pageable参数为null代表不分页
     */
    @Test
    public void queryByName(){
        List<Person> list = personRepository.findByName("赵柳",null);
        System.out.println(list);
    }


    /**
     * 分页降序查询,返回值为Page<Person>代表我不光要数据还要数据的总数等数据
     */
    @Test
    public void queryAllWithPagination(){
        Sort sort = new Sort(Sort.Direction.DESC,"id");//根据id降序
        Pageable pageable = PageRequest.of(0,2,sort);//页码从0开始,返回第一页&每页2条数据
        Page<Person> data = personRepository.findAll(pageable);
        System.out.println(data);
    }


    /**
     * 根据name字段的值进行查询,返回值为List代表虽然我分页但我只要数据
     */
    @Test
    public void queryByNameWithPagination(){
        Pageable pageable = PageRequest.of(0,2);//每页展示2条数据,需要第一页数据
        List<Person> list = personRepository.findByName("赵柳", pageable);
        System.out.println(list);
    }

    /**
     * 测试jpa批量删除
     * deleteAll会有性能问题,它是一个一个删
     * deleteInBatch不会有问题,它写成了一个sql
     */
    @Test
    public void deleteAllByName(){
        personRepository.deleteInBatch(personRepository.findByName("赵柳"));
        //personRepository.deleteAll(personRepository.findByName("赵柳"));
    }
}
