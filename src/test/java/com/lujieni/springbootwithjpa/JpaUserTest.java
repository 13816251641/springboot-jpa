package com.lujieni.springbootwithjpa;

import com.lujieni.springbootwithjpa.dao.PersonRepository;
import com.lujieni.springbootwithjpa.dao.UserRepository;
import com.lujieni.springbootwithjpa.entity.bo.PersonBO;
import com.lujieni.springbootwithjpa.entity.pojo.Person;
import com.lujieni.springbootwithjpa.entity.pojo.Person;
import com.lujieni.springbootwithjpa.entity.pojo.User;
import com.lujieni.springbootwithjpa.entity.vo.PersonVo;
import com.lujieni.springbootwithjpa.service.PersonService;
import com.lujieni.springbootwithjpa.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;


import java.util.List;


/**
 * 通用jpa操作代码测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class JpaUserTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    /**
     * 如果在repository中设置返回值为User,如果只返回
     * 一条数据当然没问题,如果是多条数据数据程序会报
     * 错,所以如果返回多条数据建议使用List接收
     */
    @Test
    public void testFindByUsername(){
        List<User> list = userRepository.findByUsername("定位");
        System.out.println(list);
    }


    @Test
    public void testGetUserByPassword(){
        List<User> user = userService.getUserByPassword("1234");
        user.forEach(t->{
            System.out.println(t.toString());
            /*这里return后循环仍旧会继续执行*/
            return;
        });
    }


    /**
     * 测试断言 OK
     */
    @Test
    public void testAssert(){
         try {
             /*使用断言时当入参不符合要求的时候就会抛出IllegalArgumentException异常*/
             Assert.notNull(null, "为null了");
         }catch (Exception e){
             log.info(e.getMessage());
         }
    }



    /**
     *测试使用@Query
     *
     *使用@Query并且nativeQuery=false返回的list里的参数类型是Object类型的;
     *如果想让他变成你想要的类型就要自己在里面new对象;
     *但如果nativeQuery=true返回直接就是实体类了,不需要new了
     */
    @Test
    public void testUseQuery(){
        /* 分页和排序条件 */
        Sort sort = new Sort(Sort.Direction.DESC,"id");//根据id降序
        Pageable pageable = PageRequest.of(0,3,sort);//页码从0开始,返回第一页&每页3条数据

        List<User> users = userRepository.selectByPasswordWithPage("123", pageable);
        users.forEach(e->{
            System.out.println(e.toString());
            return;//循环还是会进行下去
        });
    }
















}
