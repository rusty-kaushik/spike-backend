package com.in2it.spykeemployee.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.in2it.spykeemployee.entity.SocialMedia;

@Repository
public interface SocialMediaRepository extends JpaRepository<SocialMedia, UUID>{

}
