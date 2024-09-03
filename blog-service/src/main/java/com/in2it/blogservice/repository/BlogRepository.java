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
    

//	@Query(value= "select  * from blog where status='ACTIVE' and title like %:title% ", nativeQuery = true)
	 List<Blog> findByTitleContainingAllIgnoringCaseAndStatus(String title, String status);
	 
	 List<Blog> findByTitleContainingAllIgnoringCaseAndStatus(PageRequest pageable,String title, String status);
	
	@Query(value= "select  * from blog where status='ACTIVE' and author_id =%:autherId% ", nativeQuery = true)
	 List<Blog> findByAuthorId(String autherId);
	

	 @Query(value= "select  * from blog where status='ACTIVE'", nativeQuery = true)
	 List<Blog> findAll();
	
	@Query(value= "select  * from blog where id=%:id% and status='ACTIVE'", nativeQuery = true)
	Blog getByBlogId(UUID id);
		
	
	@Modifying
	@Query(value= "update  blog set  status='INACTIVE', updated_by=%:updatedBy%  , updated_date_time=%:time% where  id=%:id%", nativeQuery = true)
	 void deleteBlogById(UUID id , String updatedBy ,LocalDateTime time);
	
	@Modifying
	@Query(value= "update  blog set  status='INACTIVE', updated_date_time=%:time% , updated_by=%:updatedBy% where  id=%:blogId%", nativeQuery = true)
	void deleteByTitleContainingAllIgnoringCaseAndStatus(UUID blogId,LocalDateTime time,  String updatedBy);
	
	@Query(value= "select  * from blog where status='ACTIVE'and department_id=%:departmentId%", nativeQuery = true)
	List<Blog> getByDepartmentId(long departmentId);
}
