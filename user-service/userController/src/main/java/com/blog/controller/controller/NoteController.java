package com.blog.controller.controller;

import com.blog.controller.response.ResponseHandler;
import com.blog.repository.DTO.notes.NoteRequestDto;
import com.blog.repository.DTO.notes.NoteResponseDto;
import com.blog.service.service.noteService.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/in2it/spike/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'EMPLOYEE', 'SUPER_AUTHOR', 'MANAGER', 'TRAINEE')")
    public ResponseEntity<Object> createNewNote(@RequestBody NoteRequestDto noteRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        NoteResponseDto noteResponseDto = noteService.createNote(userName,noteRequestDto);
        return ResponseHandler.responseBuilder("Note Successfully created", HttpStatus.OK, noteResponseDto);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'EMPLOYEE', 'SUPER_AUTHOR', 'MANAGER', 'TRAINEE')")
    public ResponseEntity<Object> updateNote(@RequestParam Long noteId,@RequestBody NoteRequestDto noteRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        NoteResponseDto noteResponseDto = noteService.updateNote(userName,noteId,noteRequestDto);
        return ResponseHandler.responseBuilder("Note Successfully updated", HttpStatus.OK, noteResponseDto);
    }

    @GetMapping("/fetch")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'EMPLOYEE', 'SUPER_AUTHOR', 'MANAGER', 'TRAINEE')")
    public ResponseEntity<Object> fetchNote(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size,
                                            @RequestParam(required = false) String sortColumn,
                                            @RequestParam(required = false) String sort,
                                            @RequestParam(required = false) String keyword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Map<String, Object> result= noteService.fetchNote(page,size,sortColumn,sort,keyword,userName);
        return ResponseHandler.responseBuilder("Note Successfully updated", HttpStatus.OK, result);
    }

    @DeleteMapping("/delete/{noteId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'EMPLOYEE', 'SUPER_AUTHOR', 'MANAGER', 'TRAINEE')")
    public ResponseEntity<Object> deleteNote(@PathVariable("noteId") Long noteId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        String result= noteService.deleteNote(userName,noteId);
        return ResponseHandler.responseBuilder("Note Successfully deleted", HttpStatus.OK, result);
    }
}