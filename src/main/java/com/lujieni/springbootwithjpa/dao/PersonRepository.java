package com.lujieni.springbootwithjpa.dao;

import com.lujieni.springbootwithjpa.entity.Person;
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
