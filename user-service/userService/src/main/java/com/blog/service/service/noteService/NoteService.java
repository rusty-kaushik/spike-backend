package com.blog.service.service.noteService;

import com.blog.repository.DTO.notes.NoteRequestDto;
import com.blog.repository.DTO.notes.NoteResponseDto;

import java.util.Map;

public interface NoteService {

    NoteResponseDto createNote(String creator,NoteRequestDto noteRequest);

    NoteResponseDto updateNote(String creator, Long noteId, NoteRequestDto noteRequestDto);


    Map<String, Object> fetchNote(int page, int size, String sortColumn, String sort, String keyword, String userName);

    String deleteNote(String userName, Long noteId);
}
