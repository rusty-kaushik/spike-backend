package com.spike.Notes.NotesService;


import com.spike.Notes.CustomExceptions.NoteNotFoundException;
import com.spike.Notes.NotesDto.NotesDto;
import com.spike.Notes.NotesEntity.Color;
import com.spike.Notes.NotesEntity.NotesEntity;
import com.spike.Notes.NotesEntity.status;
import com.spike.Notes.NotesRepository.NotesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NoteServiceImpl implements NotesService {

    @Autowired
    private NotesRepository noteRepository;

    @Autowired
    private ModelMapper mapper;


    // convert notes into notesDto
    public NotesDto notesToNoteDto(NotesEntity notesEntity) {
        NotesDto noteDto = mapper.map(notesEntity, NotesDto.class);
        return noteDto;
    }

    //convert notesDto into NoteEntity
    public NotesEntity noteDtoToNotesEntity(NotesDto notesDto) {
        NotesEntity notesEntity = mapper.map(notesDto, NotesEntity.class);
        return notesEntity;
    }

    //service impl to add notes for user
    @Override
    public NotesDto createNote(Long userId) {
        NotesEntity notes = new NotesEntity();
        notes.setUserId(userId);
        notes.setContent("This is new Notes");
        notes.setColor(Color.GREEN);
        notes.setCreatedAt(LocalDate.now());
        notes.setStatus(status.ACTIVE);
        noteRepository.save(notes);
        NotesDto note = mapper.map(notes, NotesDto.class);
        return note;

    }


    // get api to fetch notes content
    @Override
    public List<NotesDto> getNoteByContent(String content, Long userId) {
        List<NotesEntity> notes = noteRepository.findAllByContent(content, userId);
        if (notes.isEmpty()) {
            throw new NoteNotFoundException("Note not found");
        }
        return notes.stream().map(this::notesToNoteDto).collect(Collectors.toList());
    }


    //service impl to get all notes for a particular user
    @Override
    public List<NotesDto> getAllNotes(long userId) {
        List<NotesEntity> notes = noteRepository.findAllByUserId(userId);
        if (notes.isEmpty()) {
           return Collections.emptyList();
        }
        return notes.stream().map(this::notesToNoteDto).collect(Collectors.toList());
    }


    // this service will delete a note by noteId
    @Override
    public NotesDto deleteNote(UUID noteId , long userId) {
        NotesEntity note = noteRepository.findNoteById(noteId);
        if (note == null) {
            throw new NoteNotFoundException("Note doesn't exist");
        } else {
            if (userId == note.getUserId()) {
                note.setStatus(status.INACTIVE);
                NotesEntity noteEntity = noteRepository.save(note);
                return notesToNoteDto(noteEntity);
            }
            else{
                throw new RuntimeException("You are not authorized to delete this note");
            }
        }

    }
    //this service will edit a note
    @Override
    public NotesDto editNote(NotesDto notesDto, UUID noteId) {
        NotesEntity note = noteRepository.findNoteById(noteId);
        if (note == null) {
            throw new NoteNotFoundException("Note doesn't exist");
        } else {
            if (notesDto.getContent() != null) {
                note.setContent(notesDto.getContent());
            }
            note.setUpdatedAt(LocalDate.now());
            NotesEntity noteEntity = noteRepository.save(note);
            NotesDto noteDto = notesToNoteDto(noteEntity);
            return noteDto;
        }
    }

    //this service will change the color of a note
    @Override
    public NotesDto changeColor(Color color, UUID noteId) {
        NotesEntity note = noteRepository.findNoteById(noteId);
        if(note==null){
            throw new NoteNotFoundException("Note doesn't exist");
        }
        note.setColor(color);
        noteRepository.save(note);
        return notesToNoteDto(note);
    }
}
