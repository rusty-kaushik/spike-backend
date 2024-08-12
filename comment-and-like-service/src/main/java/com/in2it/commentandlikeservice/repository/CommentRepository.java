package com.in2it.commentandlikeservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.in2it.commentandlikeservice.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	@Query(value= "select  * from comment where status='Active' and blog_id =%:blogId% ", nativeQuery = true)
	List<Comment> findByBlogId(long blogId);
	
	List<Comment> findByUserName(String userName);
	
	List<Comment> findByStatus(String status);
	
	@Query(value= "select  * from comment where status='Active'", nativeQuery = true)
	 List<Comment> findAll();

	@Modifying
	@Query(value= "update  comment set  status='InActive' where  id=%:id%", nativeQuery = true)
	void deleteById(Long id);
	
	
}
