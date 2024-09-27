package com.spike.SecureGate.service.externalNotesService;

import com.spike.SecureGate.DTO.notsDto.NoteUpdateDto;
import com.spike.SecureGate.enums.NotesColors;
import org.springframework.http.ResponseEntity;

public interface NotesService {

    ResponseEntity<Object> createNote(Long id);

    ResponseEntity<Object> fetchAllNotes(Long userId, String content);

    ResponseEntity<Object> deleteNotes(String id, long userId);

    ResponseEntity<Object> editNotes(String id, NoteUpdateDto content);

    ResponseEntity<Object> editNotesColor(String id, NotesColors color);
}
