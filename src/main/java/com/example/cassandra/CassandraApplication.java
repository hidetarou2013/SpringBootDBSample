package com.example.cassandra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.example.cassandra.entity.Departments;
import com.example.cassandra.entity.Employees;
import com.example.cassandra.entity.MemoBox;
import com.example.cassandra.repository.DepartmentsRepository;
import com.example.cassandra.repository.EmployeesRepository;
import com.example.cassandra.repository.MemoBoxRepository2;

/**
 * Spring Boot + Spring Data + Cassandra Sample
 *
 * [改変履歴]
 * refs #2 [add][2016-12-16] CassandraApplicationをimplements CommandLineRunnerし、初期データ投入処理を加えた。
 *
 * @author maekawa
 *
 */
@SpringBootApplication
@ComponentScan("com.example.cassandra")
public class CassandraApplication implements CommandLineRunner  {
    public static void main(String[] args) {
        SpringApplication.run(CassandraApplication.class, args);
    }

    @Autowired
	private MemoBoxRepository2 memoBoxRepository;

	@Autowired
	private DepartmentsRepository departmentsRepository;

	@Autowired
	private EmployeesRepository employeesRepository;

    @Override
    public void run(String... args) throws Exception {

    	initDataMemoBox();

    	initDataDepartments();

    	initDataEmployees();


    }

    /**
     * 初期データ投入処理：Employees
     */
	private void initDataEmployees() {
		this.employeesRepository.deleteAll();
    	this.employeesRepository.save(new Employees(1,"Taro Yamada","090-0000-9999","t-yamada@ken.jp",10));
    	this.employeesRepository.save(new Employees(2,"Kenichi Suzuki","090-9999-0009","k-suzuki@ken.jp",20));
    	this.employeesRepository.save(new Employees(3,"Ichiro Ueda","090-1234-5679","i-ueda@ken.jp",30));
    	this.employeesRepository.save(new Employees(4,"Hideaki Ito","090-9988-7769","h-ito@ken.jp.jp",40));
    	this.employeesRepository.save(new Employees(5,"Syouji Inoue","090-8765-4329","s-inoue@ken.jp",50));
	}

    /**
     * 初期データ投入処理：Departments
     */
	private void initDataDepartments() {
		this.departmentsRepository.deleteAll();
    	this.departmentsRepository.save(new Departments(10,"accounting_1"));
    	this.departmentsRepository.save(new Departments(20,"personal_1"));
    	this.departmentsRepository.save(new Departments(30,"general_1"));
    	this.departmentsRepository.save(new Departments(40,"development_1"));
	}

    /**
     * 初期データ投入処理：MemoBox
     */
	private void initDataMemoBox() {
		this.memoBoxRepository.deleteAll();
    	this.memoBoxRepository.save(new MemoBox("tanaka1","memo1"));
    	this.memoBoxRepository.save(new MemoBox("tanaka2","memo2"));
	}
}
