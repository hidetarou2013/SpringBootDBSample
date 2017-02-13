package com.example.cassandra.repository;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.cassandra.entity.Label;


@Repository
public interface LabelRepository extends CassandraRepository<Label> {

	@Query("SELECT * FROM MAC000001 WHERE label_name=?0")
	public List<Label> findByLabelName(String s);

	@Query("SELECT * FROM MAC000001 WHERE kigyou_cd =?0 and label_id=?1")
	public Label findByLabelId(String kigyoCD,int labelID);

	@Query("DELETE FROM MAC000001 WHERE label_name=?0")
	public List<Label> deleteByLabelName(String s);

	@Query("DELETE FROM MAC000001 WHERE kigyou_cd =?0 and label_id=?1")
	public List<Label> deleteByLabelId(String kigyou_cd,int s);


}