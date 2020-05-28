package com.mybatis.demo;

import java.util.List;

import org.javers.core.Changes;
import org.javers.core.Javers;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.repository.jql.QueryBuilder;
import org.javers.shadow.Shadow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mybatis.demo.Repository.AddressMyBatisRepository;
import com.mybatis.demo.Repository.EmployeeMyBatisRepository;
import com.mybatis.demo.bean.Address;
import com.mybatis.demo.bean.Employee;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private EmployeeMyBatisRepository employeeRepository;
	
	@Autowired
	private AddressMyBatisRepository addressMyBatisRepository;
	
	@Autowired
	private Javers javers;

    @Override
    public void run(String...args) throws Exception {

        logger.info("Inserting -> {}", employeeRepository.insert(new Employee(10011L , "Ramesh", "Fadatare", "ramesh@gmail.com")));
        logger.info("Inserting -> {}", employeeRepository.insert(new Employee(10012L , "John", "Cena", "john@gmail.com")));
        logger.info("Inserting -> {}", employeeRepository.insert(new Employee(10013L , "tony", "stark", "stark@gmail.com")));
        
        logger.info("Inserting -> {}", addressMyBatisRepository.insert(new Address(1011L , "Noida", 10011L)));
        logger.info("Inserting -> {}", addressMyBatisRepository.insert(new Address(1012L , "Gurgoan", 10012L)));
        logger.info("Inserting -> {}", addressMyBatisRepository.insert(new Address(1013L , "Pune", 10013L)));
        

        logger.info("Employee id 10011 -> {}", employeeRepository.findById(10011L));

        logger.info("Update 10011 -> {}", employeeRepository.update(new Employee(10011L , "ram", "Stark", "ramesh123@gmail.com")));
        
        logger.info("Update 10011 -> {}", employeeRepository.update(new Employee(10011L , "ram", "sharma", "ramesh123@gmail.com")));
        
        logger.info("Update 10011 -> {}", employeeRepository.update(new Employee(10011L , "ram", "sharma", "rameshsharma123@gmail.com")));
        
        employeeRepository.deleteById(employeeRepository.findById(10012L));

        logger.info("All users -> {}", employeeRepository.findAll());
        
        logger.info("Update 10011 -> {}", addressMyBatisRepository.update(new Address(1011L , "NCR", 10011L)));
        
        Changes changes2 = javers.findChanges( QueryBuilder.anyDomainObject().build());
        
        //System.out.println("changes domain object "+changes2);

        String json = javers.getJsonConverter().toJson(changes2);
        System.out.println("JSON - "+json);
   /*     
        List<Shadow<Employee>> shadow = javers.findShadows(QueryBuilder.byInstanceId("10011",com.mybatis.demo.bean.Employee.class).build());
        
        
        
        shadow.forEach(shadows -> {
        	System.out.println("shadow - "+shadows.get());
        });
        
        List<CdoSnapshot> snapshots = javers.findSnapshots(QueryBuilder.byInstanceId("10011",com.mybatis.demo.bean.Employee.class).build());
        String json = javers.getJsonConverter().toJson(snapshots);
        System.out.println("JSON - "+json);
        snapshots.forEach(snapshot -> {
        	//snapshot.getChanged().forEach(change -> {
        		//System.out.println("snapshot - change - "+change);
        		System.out.println("snapshot - "+snapshot);
        	//});
        });
        
        //javers = JaversBuilder.javers().build();
		Changes changes = javers.findChanges(QueryBuilder.byInstanceId("10011",com.mybatis.demo.bean.Employee.class).build());
		
		changes.groupByCommit().forEach(g -> {
			System.out.println(g.prettyPrint());
		});
		System.out.println("Instance change - "+changes.prettyPrint());
		
		
		Changes changes1 = javers.findChanges(QueryBuilder.byClass(Employee.class).withNewObjectChanges().build());

		System.out.println("Printing Changes with grouping by commits and by objects :");
		changes1.groupByCommit().forEach(byCommit -> {
			System.out.println("commit " + byCommit.getCommit().getId());
			byCommit.groupByObject().forEach(byObject -> {
				System.out.println("  changes on " + byObject.getGlobalId().value() + " : ");
				byObject.get().forEach(change -> {
					System.out.println("  - " + change);
				});
			});
		});
		*/
    }

    @Bean
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
