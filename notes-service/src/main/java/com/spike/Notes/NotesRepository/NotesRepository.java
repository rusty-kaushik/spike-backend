package com.spike.Notes.NotesRepository;

import com.spike.Notes.NotesDto.NotesDto;
import com.spike.Notes.NotesEntity.NotesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface NotesRepository extends JpaRepository<NotesEntity, UUID> {

    @Query(" FROM NotesEntity  Where userId=:userId AND status='ACTIVE'")
    List<NotesEntity> findAllByUserId(long userId);

    @Query("From NotesEntity Where id=:noteId AND status='ACTIVE'")
    NotesEntity findNoteById(UUID noteId);

     //this query like fetch notes based on content and ignore case-sensitivity and support partial searches
    @Query("From NotesEntity Where userId=:userId AND LOWER(content) LIKE CONCAT('%', LOWER(:content), '%') AND status='ACTIVE' ")
    List<NotesEntity> findAllByContent(@Param("content") String content, @Param("userId") long userId);
}
