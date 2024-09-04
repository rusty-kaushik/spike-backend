package com.in2it.spykeemployee.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.in2it.spykeemployee.entity.MediaFile;

@Repository
public interface MediaFileRepository extends JpaRepository<MediaFile, UUID>{

	
}
