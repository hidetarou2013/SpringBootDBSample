package com.example.cassandra.repository;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.cassandra.entity.Tag;


@Repository
public interface TagRepository extends CassandraRepository<Tag> {

	@Query("SELECT * FROM TAC000001 WHERE tag_name=?0")
	public List<Tag> findByTagName(String s);

	@Query("SELECT * FROM MAC000001 WHERE kigyou_cd =?0 and attibute_id =?1 and tag_id=?2")
	public Tag findByTagId(String kigyoCD,String attibuteId,int labelID);

	//案1－02の対応
	@Query("SELECT * FROM TAC000001 WHERE kigyou_cd =?0 and attibute_id =?1 and tag_id=?2")
	public Tag findByKeys(String kigyoCD,String attibuteId,int tag_id);


	//案1－02のタグテーブル対応
	@Query("SELECT * FROM TAC000001 WHERE label_id=?0")
	public List<Tag> findTagsByTag(int labelID);


	@Query("DELETE FROM TAC000001 WHERE label_name=?0")
	public List<Tag> deleteByTagName(String s);

	@Query("DELETE FROM TAC000001 WHERE  kigyou_cd =?0 and attibute_id =?1 and tag_id=?2")
	public List<Tag> deleteByTagId(String kigyou_cd,String attibute_id,int s);


}