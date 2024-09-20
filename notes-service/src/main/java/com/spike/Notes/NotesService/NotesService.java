package com.spike.Notes.NotesService;

import com.spike.Notes.NotesDto.NotesDto;
import com.spike.Notes.NotesEntity.Color;
import com.spike.Notes.NotesEntity.NotesEntity;

import java.util.List;
import java.util.UUID;

public interface NotesService {
    NotesDto createNote(Long userId);

    List<NotesDto> getNoteByContent(String content, Long userId);

    List<NotesDto> getAllNotes(long userId);

    NotesDto deleteNote(UUID noteId);

    NotesDto editNote(NotesDto notesDto, UUID noteId);

    String changeColor(Color color, UUID noteId);
}
