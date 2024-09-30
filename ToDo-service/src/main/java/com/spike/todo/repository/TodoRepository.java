package com.spike.todo.repository;

import com.spike.todo.entity.TODO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<TODO,Long> {
}