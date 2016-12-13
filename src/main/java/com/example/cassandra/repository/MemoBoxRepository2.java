package com.example.cassandra.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.cassandra.entity.MemoBox;

@Repository
public interface MemoBoxRepository2 extends CassandraRepository<MemoBox> {

	@Query("SELECT * FROM memobox WHERE name=?")
	public List<MemoBox> findByName(String s);
	@Query("SELECT * FROM memobox WHERE id=?")
	public MemoBox findById(UUID s);
	@Query("DELETE FROM memobox WHERE name=?")
	public List<MemoBox> deleteByName(String s);
	@Query("DELETE FROM memobox id=?")
	public List<MemoBox> deleteById(UUID s);

//    @Query("SELECT * FROM memobox WHERE name=?0 LIMIT ?1")
//    Iterable<Greeting> findByName(String name,Integer limit);

}