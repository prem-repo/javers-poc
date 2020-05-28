package com.mybatis.demo.controller;

import java.util.List;

import org.javers.core.Changes;
import org.javers.core.Javers;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.repository.jql.QueryBuilder;
import org.javers.shadow.Shadow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mybatis.demo.Repository.AddressMyBatisRepository;
import com.mybatis.demo.Repository.EmployeeMyBatisRepository;
import com.mybatis.demo.bean.Address;
import com.mybatis.demo.bean.Employee;

@RestController
/*@RequestMapping("/hello")*/
public class EmployeeController {

	@Autowired
	EmployeeMyBatisRepository employeeMyBatisRepository;
	
	@Autowired
	AddressMyBatisRepository addressMyBatisRepository;
	
	@Autowired
	Javers javers;
	
	@RequestMapping(method = RequestMethod.GET, path="/snapshot", produces="application/json")
	public ResponseEntity<String> getEmployeesSnapshot() {
		
		 //List<CdoSnapshot> snapshots = javers.findSnapshots(QueryBuilder.byInstanceId("10014",com.mybatis.demo.bean.Employee.class).withNewObjectChanges(true).build());
		List<CdoSnapshot> snapshots = javers.findSnapshots( QueryBuilder.anyDomainObject().build());
		 String json = javers.getJsonConverter().toJson(snapshots);
		 return new ResponseEntity<>(json, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, path="/change", produces="application/json")
	public ResponseEntity<String> getEmployeesChange() {
		
		 //Changes changes = javers.findChanges(QueryBuilder.byInstanceId("10014",com.mybatis.demo.bean.Employee.class).withNewObjectChanges(true).build());
		Changes changes = javers.findChanges( QueryBuilder.anyDomainObject().byAuthor("admin").build());
		 String json = javers.getJsonConverter().toJson(changes);
		 return new ResponseEntity<>(json, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, path="/shadow", produces="application/json")
	public ResponseEntity<String> getEmployeesShadow() {
		
		//List<Shadow<Employee>> shadow = javers.findShadows(QueryBuilder.byInstanceId("10014",com.mybatis.demo.bean.Employee.class).byAuthor("user").build());
		List<Shadow<Employee>> shadow = javers.findShadows( QueryBuilder.anyDomainObject().byAuthor("user").build());
		 String json = javers.getJsonConverter().toJson(shadow);
		 return new ResponseEntity<>(json, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, path="/employees", produces="application/json")
	public List<Employee> getEmployees() {
		return employeeMyBatisRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.POST, path = "/addEmployee", consumes = "application/json")
	public void addEmployee(@RequestBody Employee employee) {
		employeeMyBatisRepository.insert(employee);
	}
	
	@RequestMapping(method = RequestMethod.PUT, path = "/updateEmployee", consumes = "application/json")
	public int updateEmployee(@RequestBody Employee employee) {
		return employeeMyBatisRepository.update(employee);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/getEmployeeById", produces = "application/json")
	public Employee getUser(@RequestParam int employeeId) {
		return employeeMyBatisRepository.findById(new Long(employeeId));
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path = "/deleteEmployee", consumes = "application/json")
	public int deleteEmployee(@RequestBody Employee employee) {
		return employeeMyBatisRepository.deleteById(employee);
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/addAddress", consumes = "application/json")
	public void addAddress(@RequestBody Address address) {
		addressMyBatisRepository.insert(address);
	}
	
	@RequestMapping(method = RequestMethod.PUT, path = "/updateAddress", consumes = "application/json")
	public int updateAddress(@RequestBody Address address) {
		return addressMyBatisRepository.update(address);
	}
	
	@RequestMapping(method = RequestMethod.GET, path="/address", produces="application/json")
	public List<Address> getAddress() {
		return addressMyBatisRepository.findAll();
	}
}
