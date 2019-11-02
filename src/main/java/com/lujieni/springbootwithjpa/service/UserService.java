package com.lujieni.springbootwithjpa.service;

import com.lujieni.springbootwithjpa.dao.UserRepository;
import com.lujieni.springbootwithjpa.entity.pojo.Person;
import com.lujieni.springbootwithjpa.entity.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

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

    /**
     * 根据密码查询用户
     */
    public List<User> getUserByPassword(String password) {
       return userRepository.findAll((Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate p1 = criteriaBuilder.equal(root.get("password"), password);
            return criteriaBuilder.and(p1);
        });
          /*
          匿名类
          userRepository.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate p1 = criteriaBuilder.equal(root.get("password"), password);
                return criteriaBuilder.and(p1);
            }
          });
        */
    }
}
