package com.lujieni.springbootwithjpa;

import com.lujieni.springbootwithjpa.dao.PersonRepository;
import com.lujieni.springbootwithjpa.dao.UserRepository;
import com.lujieni.springbootwithjpa.entity.bo.PersonBO;
import com.lujieni.springbootwithjpa.entity.pojo.Person;
import com.lujieni.springbootwithjpa.entity.pojo.Person;
import com.lujieni.springbootwithjpa.entity.pojo.User;
import com.lujieni.springbootwithjpa.entity.vo.PersonVo;
import com.lujieni.springbootwithjpa.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 通用jpa操作代码测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class JpaCommonTest {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PersonService personService;
    @Autowired
    private UserRepository userRepository;


    @Test
    public void test(){
     try {
         /*使用断言时当入参不符合要求的时候就会抛出IllegalArgumentException异常*/
         Assert.notNull(null, "为null了");
     }catch (Exception e){
         log.info(e.getMessage());
     }
    }



    /**
     *
     * 测试使用@Query  OK
     *
     *使用@Query返回的list里的参数类型是Object类型的,
     *如果你想要让他变成你想要的类型就要自己在里面加
     *入new
     */
    @Test
    public void testUseQuery(){
        Sort sort = new Sort(Sort.Direction.DESC,"id");//根据id降序
        Pageable pageable = PageRequest.of(2,3,sort);//页码从0开始,返回第一页&每页2条数据
        List<User> users = userRepository.selectByPasswordWithPage("123", pageable);
        System.out.println("hello");
    }


    /**
     * 测试多事务下的回滚 OK
     * 1.事务的默认传播级别是有就依附没有就创建
     * 2.默认传播级别会导致事务一起生效一起回滚但
     *   对于日志表来说即使失败也要记一笔所以可以
     *   使用@Transactional(propagation = Propagation.REQUIRES_NEW)
     *   来代表这个事务将当前事务挂起重新构建一个事务
     */
    @Test
    public void testMultiTransaction(){
        personService.insertOne();
    }


    /**
     * 测试使用@Query进行删除
     * 1.@Query不支持新增
     * 2.@Query如果是修改和删除操作一定要加@Modifying
     * 3.@Query+@Modifying一定需要事务支持才行
     */
    @Test
    public void testDeleteDataUseQuery(){
        personService.deleteDataUseQuery(1L);
    }

    /**
     * 测试事务的回滚  OK
     */
    @Test
    @Transactional(rollbackFor = Exception.class)
    public void testTransaction(){
            Person person = new Person();
            person.setAge(100);
            personRepository.save(person);
            int i= 5/0;
    }


    /**
     * 测试利用注解自动生成创建时间和更新时间 OK
     */
    @Test
    public void testAutoSetTime(){
        Optional<Person> option = personRepository.findById(24L);
        if(option.isPresent()){
            Person person = option.get();
            person.setAge(1);
            personRepository.save(person);
        }
    }


    @Test
    public void testGroupByName(){
        List<PersonBO> list = personRepository.getPersonGroupByName();
        System.out.println(list);
    }

    /**
     *测试利用name进行分组后的个数
     */
    @Test
    public void testCountGroupByName(){
        List<Integer> list = personRepository.getCountGroupByName();
        System.out.println(list);
    }



    /**
     * 加入了where查询
     * select name,age,count(age) from person where
     * birth between ? and ? group by name,age
     */
    @Test
    public void testWithCriteria3() throws Exception{
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PersonVo> query = criteriaBuilder.createQuery(PersonVo.class);
        Root<Person> root = query.from(Person.class);

        List<Selection<?>> selections = new ArrayList<>();//构造查询字段
        selections.add(root.get("name"));
        selections.add(root.get("age"));
        selections.add(criteriaBuilder.count(root.get("age")));
        query.multiselect(selections);//使用multiselect多维度查询

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start = sdf.parse("2019-10-08");
        Date end = sdf.parse("2019-10-09");
        Predicate birth = criteriaBuilder.between(root.get("birth"), start, end);
        query.where(birth);


        List<Expression<?>> groupbys = new ArrayList<>();//构造group by条件,前后顺序对结果没有关系
        groupbys.add(root.get("name"));
        groupbys.add(root.get("age"));
        query.groupBy(groupbys);
        List<PersonVo> resultList = entityManager.createQuery(query).getResultList();
        System.out.println(resultList.size());
        resultList.forEach(o->{
            System.out.println(o.toString());
        });
    }


    /**
     * 将查询结果封装为vo对象,vo对象必须要有对应的构造方法,顺序也要一致才行
     * select name,age,count(age) from Person group by name,age;
     */
    @Test
    public void testWithCriteria2(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PersonVo> query = criteriaBuilder.createQuery(PersonVo.class);
        Root<Person> root = query.from(Person.class);

        List<Selection<?>> selections = new ArrayList<>();//构造查询字段
        selections.add(root.get("name"));
        selections.add(root.get("age"));
        selections.add(criteriaBuilder.count(root.get("age")));
        query.multiselect(selections);//使用multiselect多维度查询

        List<Expression<?>> groupbys = new ArrayList<>();//构造group by条件,前后顺序对结果没有关系
        groupbys.add(root.get("name"));
        groupbys.add(root.get("age"));
        query.groupBy(groupbys);
        List<PersonVo> resultList = entityManager.createQuery(query).getResultList();
        resultList.forEach(o->{
            System.out.println(o.toString());
        });
    }

    /**
     * 使用Criteria进行动态查询,最后的sql为
     * select count(id) from person group by name,age order by count(id) desc;
     */
    @Test
    public void testWithCriteria1(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);//查询结果的类型
        Root<Person> root = query.from(Person.class);//查询哪张表
        query.select(criteriaBuilder.count(root.get("id")));//select count(id)
        /*
            Predicate predicate = criteriaBuilder.equal(root.get("age"), 28);
            query.where(predicate);
        *//*
         */
        /*
           加入group by维度,注意group by中字段的先后顺序不会影响到结果
         */
        List<Expression<?>> list = new ArrayList<>();
        list.add(root.get("name"));
        list.add(root.get("age"));
        query.groupBy(list);
        query.orderBy(criteriaBuilder.desc(criteriaBuilder.count(root.get("id"))));//order by
        List<Long> resultList = entityManager.createQuery(query).getResultList();
        System.out.println(resultList);
    }


    /**
     * 使用example查询
     */
    @Test
    public void testWithExample(){
        Person person = new Person();
        person.setId(1L);
        person.setName("陆捷旎A");
        Example<Person> example = Example.of(person);
        List<Person> list = personRepository.findAll(example);
        System.out.println(list.size());

    }

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
