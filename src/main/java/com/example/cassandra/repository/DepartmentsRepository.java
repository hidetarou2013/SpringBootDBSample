package com.example.cassandra.repository;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.cassandra.entity.Departments;


@Repository
public interface DepartmentsRepository extends CassandraRepository<Departments> {

	@Query("SELECT * FROM departments WHERE department_name=?0")
	public List<Departments> findByDepartmentName(String s);

	@Query("SELECT * FROM departments WHERE department_id=?0")
	public Departments findByDepartmentId(int s);

	@Query("DELETE FROM departments WHERE department_name=?0")
	public List<Departments> deleteByDepartmentName(String s);

	@Query("DELETE FROM departments WHERE department_id=?0")
	public List<Departments> deleteByDepartmentId(int s);


}