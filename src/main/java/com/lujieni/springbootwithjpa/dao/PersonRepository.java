package com.lujieni.springbootwithjpa.dao;

import com.lujieni.springbootwithjpa.entity.bo.PersonBO;
import com.lujieni.springbootwithjpa.entity.pojo.Person;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 这里什么注解都不用配置,好神奇
 */
public interface PersonRepository extends JpaRepository<Person,Long> {

    @Query(value = "select * from person where hobby_name=?1",nativeQuery = true)
    List<Person> findAllByHobbyName(String hobbyName);

    /**
     * 根据name字段分组,查询每组的个数
     * @return
     */
    @Query(value = "select count(1) from person group by name",nativeQuery = true)
    List<Integer> getCountGroupByName();

    /**
     * 根据name字段分组,查询每组的个数和每组的name
     * 封装了返回实体类,可以看到sql语句里有new,因此
     * nativesql一定不能为true,道理很简单,一旦为true
     * jpa就直接给数据库执行了导致无法实现返回自定义
     * 类型的功能
     * @return
     */
    @Query(value = "select new com.lujieni.springbootwithjpa.entity.bo.PersonBO(name,count (name)) from Person group by name")
    List<PersonBO> getPersonGroupByName();


    /**
     * 根据name进行左模糊查询,jpa会自动帮你在字段左边加入%
     * @param name name字段值
     * @return
     */
    List<Person> findByNameEndingWith(String name);

    /**
     * 根据name进行全模糊查询,入参一定要带%
     * @param name name字段值
     * @return
     */
    List<Person> findByNameLike(String name);

    /**
     *
     * @param name  name字段值
     * @param pageable 分页参数,如果为null代表不分页
     * @return
     */
    List<Person> findByName(String name, Pageable pageable);

    /**
     *
     * @param name name字段值
     * @return
     */
    List<Person> findByName(String name);

}
