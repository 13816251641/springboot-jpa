package com.lujieni.springbootwithjpa.service;

import com.lujieni.springbootwithjpa.dao.PersonRepository;
import com.lujieni.springbootwithjpa.entity.pojo.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther ljn
 * @Date 2019/10/27
 */
@Service
@Slf4j
public class PersonService  {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private UserService userService;

    /**
     *
     * @param id 数据id
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteDataUseQuery(Long id){
        personRepository.deleteData(id);
    }


    /**
     * 在Person表内插入一行数据
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertOne(){
        try {
            Person person = new Person();
            person.setName("阿华田");
            personRepository.save(person);
            int i = 5/0;
        } catch (Exception e) {
            log.info("异常方法执行");
            userService.insertOne();
            throw  new RuntimeException();
        }

    }



}
