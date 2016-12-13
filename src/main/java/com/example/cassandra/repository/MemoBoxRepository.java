package com.example.cassandra.repository;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.cassandra.entity.MemoBox;
import com.gkatzioura.spring.entity.Greeting;

@Repository
public interface MemoBoxRepository extends CassandraRepository<MemoBox> {

	public List<MemoBox> findByName(String s);
	public MemoBox findById(String s);
	public List<MemoBox> deleteByName(String s);
	public List<MemoBox> deleteById(String s);

    @Query("SELECT * FROM memobox WHERE name=?0 LIMIT ?1")
    Iterable<Greeting> findByName(String name,Integer limit);

}