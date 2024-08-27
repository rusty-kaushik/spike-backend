package com.blog.repository.repository;

import com.blog.repository.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DepartmentRepository  extends JpaRepository<Department, Long> {
    Optional<Department> findByName(String name);

    @Query("SELECT t FROM Department t WHERE t.deletedAt IS NULL")
    Page<Department> findAllActive(Pageable pageable);

    @Query("SELECT t FROM Department t WHERE t.deletedAt IS NOT NULL")
    List<Department> findAllDeleted();

    @Query("SELECT t FROM Department t WHERE t.name LIKE %:name% AND t.deletedAt IS NULL")
    Page<Department> findByNameContainingIgnoreCaseActive(@Param("name") String name, Pageable pageable);

    @Query("SELECT t FROM Department t WHERE t.id = :id AND t.deletedAt IS NULL")
    Optional<Department> findByIdActive(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Department t SET t.deletedAt = :deletedAt, t.deletedBy = :deletedBy WHERE t.id = :id AND t.deletedAt IS NULL")
    void softDelete(@Param("id") Long id, @Param("deletedBy") String deletedBy, @Param("deletedAt") LocalDateTime deletedAt);

    @Modifying
    @Query("UPDATE Department t SET t.deletedAt = NULL, t.deletedBy = NULL WHERE t.id = :id AND t.deletedAt IS NOT NULL")
    void restore(@Param("id") Long id);
}
