package com.in2it.blogservice.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.in2it.blogservice.model.Blog;




@Repository
public interface BlogRepository extends JpaRepository<Blog, Long>{
    

//	@Query(value= "select  * from blog where status='ACTIVE' and title like %:title% ", nativeQuery = true)
	 List<Blog> findByTitleContainingAllIgnoringCaseAndStatus(String title, String status);
	
	@Query(value= "select  * from blog where status='ACTIVE' and author_id =%:autherId% ", nativeQuery = true)
	 List<Blog> findByAuthorId(String autherId);
	

	@Query(value= "select  * from blog where status='ACTIVE'", nativeQuery = true)
	 List<Blog> findAll();
	
	@Query(value= "select  * from blog where id=%:id% and status='ACTIVE'", nativeQuery = true)
	Blog getByBlogId(long id);
		
	
	@Modifying
	@Query(value= "update  blog set  status='INACTIVE', deleted_by=%:deletedBy%  , deleted_date_time=%:time% where  id=%:id%", nativeQuery = true)
	 void deleteBlogById(long id , String deletedBy ,LocalDateTime time);
	
	@Modifying
	@Query(value= "update  blog set  status='INACTIVE', deleted_by=%:autherId%  , deleted_date_time=%:time% where  id=%:blogId%", nativeQuery = true)
	void deleteByTitleContainingAllIgnoringCaseAndStatus(long blogId, String autherId ,LocalDateTime time);
	
	@Query(value= "select  * from blog where status='ACTIVE'and team_id=%:teamId%", nativeQuery = true)
	List<Blog> getByTeamId(long teamId);
}
