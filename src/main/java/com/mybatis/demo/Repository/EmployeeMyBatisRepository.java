package com.mybatis.demo.Repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.javers.spring.annotation.JaversAuditable;

import com.mybatis.demo.bean.Employee;

@Mapper
/*@JaversSpringDataAuditable*/
public interface EmployeeMyBatisRepository {

	@Select("select * from employees")
    public List < Employee > findAll();

    @Select("SELECT * FROM employees WHERE id = #{id}")
    public Employee findById(Long id);

    @Delete("DELETE FROM employees WHERE id = #{id}")
    @JaversAuditable
    public int deleteById(Employee employee);

    @Insert("INSERT INTO employees(id, first_name, last_name,email_address) " +
        " VALUES (#{id}, #{firstName}, #{lastName}, #{emailId})")
    @JaversAuditable
    public int insert(Employee employee);

    @Update("Update employees set first_name=#{firstName}, " +
        " last_name=#{lastName}, email_address=#{emailId} where id=#{id}")
    @JaversAuditable
    public int update(Employee employee);
}
