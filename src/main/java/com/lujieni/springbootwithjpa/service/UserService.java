package com.lujieni.springbootwithjpa.service;

import com.lujieni.springbootwithjpa.dao.UserRepository;
import com.lujieni.springbootwithjpa.entity.pojo.Person;
import com.lujieni.springbootwithjpa.entity.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther ljn
 * @Date 2019/10/27
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    /**
     * 在User表内插入一行数据
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertOne(){
        User user = new User();
        user.setUsername("阿华田");
        userRepository.save(user);
    }

}
