package com.mybatis.demo.Repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.javers.spring.annotation.JaversAuditable;

import com.mybatis.demo.bean.Address;

@Mapper
public interface AddressMyBatisRepository {
	@Select("select * from address")
	public List<Address> findAll();

	@Select("SELECT * FROM address WHERE id = #{id}")
	public Address findById(Long id);

	@Insert("INSERT INTO address(addressId, address, id) " + " VALUES (#{addressId}, #{address}, #{id})")
	@JaversAuditable
	public int insert(Address address);

	@Update("Update address set address=#{address}, " + " id=#{id} where addressId=#{addressId}")
	@JaversAuditable
	public int update(Address address);

}
