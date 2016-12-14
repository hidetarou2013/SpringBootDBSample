package com.example.cassandra.repository;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.cassandra.entity.Employees;


@Repository
public interface EmployeesRepository extends CassandraRepository<Employees> {

	@Query("SELECT * FROM employees WHERE employee_name=?0")
	public List<Employees> findByEmployeeName(String s);

	@Query("SELECT * FROM employees WHERE employee_id=?0")
	public Employees findByEmployeeId(int s);

	@Query("DELETE FROM employees WHERE employee_name=?0")
	public List<Employees> deleteByEmployeeName(String s);

	@Query("DELETE FROM employees WHERE employee_id=?0")
	public List<Employees> deleteByEmployeeId(int s);


}