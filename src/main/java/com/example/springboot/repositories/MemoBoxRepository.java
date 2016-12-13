package com.example.springboot.repositories;



import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.springboot.MemoBox;

@Repository
public interface MemoBoxRepository extends MongoRepository<MemoBox, Long> {

	public List<MemoBox> findByName(String s);
	public MemoBox findById(String s);
	public List<MemoBox> deleteByName(String s);
	public List<MemoBox> deleteById(String s);

}
