package com.blog.service.service.noteService;

import com.blog.repository.DTO.notes.NoteRequestDto;
import com.blog.repository.DTO.notes.NoteResponseDto;
import com.blog.service.exceptions.UnexpectedException;
import com.blog.service.externalServices.ExternalNotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NoteServiceImpl implements NoteService{

    @Autowired
    private ExternalNotesService externalNotesService;

    @Override
    public NoteResponseDto createNote(String creator, NoteRequestDto noteRequest) {
        try{
            return externalNotesService.create(creator,noteRequest);
        } catch (Exception e) {
            throw new UnexpectedException("Unable to create note");
        }
    }

    @Override
    public NoteResponseDto updateNote(String creator, Long noteId, NoteRequestDto noteRequestDto) {
        try {
            return externalNotesService.update(creator, noteId, noteRequestDto);
        } catch (Exception e) {
            throw new UnexpectedException("Unable to update note");
        }
    }

    @Override
    public Map<String, Object> fetchNote(int page, int size, String sortColumn, String sort, String keyword, String userName) {
        try {
            return externalNotesService.fetch(page, size, sortColumn, sort, keyword, userName);
        } catch (Exception e) {
            throw new UnexpectedException("Unable to fetch notes");
        }
    }

    @Override
    public String deleteNote(String userName, Long noteId) {
        try{
            return externalNotesService.delete(userName, noteId);
        } catch (Exception e) {
            throw new UnexpectedException("Unable to fetch notes");
        }
    }
}
