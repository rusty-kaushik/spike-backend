package com.in2it.blogservice.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.in2it.blogservice.model.Blog;




@Repository
public interface BlogRepository extends JpaRepository<Blog,UUID>{
    


	 List<Blog> findByTitleContainingAllIgnoringCaseAndStatus(String title, Boolean status);
	 
	 List<Blog> findByTitleContainingAllIgnoringCaseAndStatus(PageRequest pageable,String title, Boolean status);
	
	@Query(value= "select  * from blog where status=true and user_name =%:userName% ", nativeQuery = true)
	 List<Blog> findByAuthorName(String userName);
	
	@Query(value= "select  * from blog where status=true and user_id =%:userId% ", nativeQuery = true)
	List<Blog> findByAuthorId(long userId);
	
	

	 @Query(value= "select  * from blog where status=true", nativeQuery = true)
	 List<Blog> findAll(PageRequest pageable, Boolean status);
	
	@Query(value= "select  * from blog where id=%:id% and status=true", nativeQuery = true)
	Blog getByBlogId(UUID id);
		
	
	@Modifying
	@Query(value= "update  blog set  status=false, updated_by=%:updatedBy%  , updated_date_time=%:time% where  id=%:id%", nativeQuery = true)
	 void deleteBlogById(UUID id , String updatedBy ,LocalDateTime time);
	
	@Modifying
	@Query(value= "update  blog set  status=false, updated_date_time=%:time% , updated_by=%:updatedBy% where  id=%:blogId%", nativeQuery = true)
	void deleteByTitleContainingAllIgnoringCaseAndStatus(UUID blogId,LocalDateTime time,  String updatedBy);
	
	@Query(value= "select  * from blog where status=true and department_id=%:departmentId%", nativeQuery = true)
	List<Blog> getByDepartmentId(long departmentId);
	
	List<Blog> findByStatus(boolean status);
}
