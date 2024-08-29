package com.in2it.blogmongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.in2it.blogmongo.model.Media;

@Repository
public interface MediaRepository extends MongoRepository<Media, String> {
	
	

}
