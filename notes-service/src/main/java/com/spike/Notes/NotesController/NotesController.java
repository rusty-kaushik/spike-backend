package com.spike.Notes.NotesController;

import com.spike.Notes.CustomExceptions.NoteNotFoundException;
import com.spike.Notes.NotesDto.NotesDto;
import com.spike.Notes.NotesEntity.Color;
import com.spike.Notes.NotesService.NotesService;
import com.spike.Notes.ResponseHandler.ResponseHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("spike")
public class NotesController {

    @Autowired
    private NotesService noteService;

    @Autowired
    private ResponseHandler responseHandler;

    //Post Api to create new Note
    @Operation(summary = "Creates a New Note")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "note created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid file format")
    })
    @PostMapping("/create-note/{userId}")
    public ResponseEntity<Object> createNotes(@PathVariable("userId") Long userId) {
        try {
            NotesDto note = noteService.createNote(userId);
            return responseHandler.response("Note created successfully", HttpStatus.CREATED, note);
        } catch (RuntimeException ex) {
            throw new RuntimeException("Error occurred while creating notes", ex);
        }
    }


    //Get Api to fetch particular Note by note-content if provided , otherwise will fetch all the notes by userid
    @Operation(summary = "Fetch All Notes Either By Content or By UserId")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "notes fetched successfully"),
            @ApiResponse(responseCode = "400", description = "Error occurred while fetching notes"),
            @ApiResponse(responseCode = "204", description = "Note not found")
    })
    @GetMapping("/get-notes/{userId}")
    public ResponseEntity<Object> getNotes(@RequestParam(name = "content", required = false) String content, @PathVariable Long userId) {
        try {
            if (content != null) {
                List<NotesDto> note = noteService.getNoteByContent(content, userId);
                return responseHandler.response("Note fetched successfully", HttpStatus.OK, note);
            } else {
                List<NotesDto> notes = noteService.getAllNotes(userId);
                return responseHandler.response("Notes fetched successfully", HttpStatus.OK, notes);
            }
        } catch (NoteNotFoundException ex) {
            return responseHandler.response("Note Not Found",HttpStatus.OK, "Note not found");
        } catch (RuntimeException ex) {
            throw new RuntimeException("Error occurred while fetching notes");
        }
    }


    //Delete api to delete a particular note by noteId
    @Operation(summary = "Delete a Note")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Note deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Error occurred while fetching notes"),
            @ApiResponse(responseCode = "404", description = "Note not found")
    })
    @DeleteMapping("/delete-note/{id}/{userId}")
    public ResponseEntity<Object> deleteNote(@PathVariable String id,@PathVariable  Long userId) {
        try {
            UUID noteId = UUID.fromString(id);
            NotesDto note = noteService.deleteNote(noteId, userId);
            return responseHandler.response("Note deleted successfully", HttpStatus.OK, note);
        } catch (NoteNotFoundException ex) {
            throw new NoteNotFoundException("Note not found with id : " + id);
        } catch (RuntimeException ex) {
            throw ex;
        }
    }


    //Put Api to edit a particular note by NoteId
    @Operation(summary = "Edit a Note")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Note edited successfully"),
            @ApiResponse(responseCode = "400", description = "Error occurred while fetching notes"),
            @ApiResponse(responseCode = "404", description = "Note not found")
    })
    @PutMapping("edit-note/{id}")
    public ResponseEntity<Object> EditNote(@PathVariable String id, @RequestBody NotesDto notesDto) {
        try {
            UUID noteId = UUID.fromString(id);
            NotesDto note = noteService.editNote(notesDto, noteId);
            return responseHandler.response("Note edited successfully", HttpStatus.OK, note);
        } catch (NoteNotFoundException ex) {
            throw new NoteNotFoundException("Note not found with id : " + id);
        } catch (RuntimeException ex) {
            throw new RuntimeException("Error occurred while editing a note");
        }
    }

    //put api to change the color of note
    @PutMapping("change-note-color/{id}/{color}")
    private ResponseEntity<Object> ChangeColorOfNote(@PathVariable String id, @PathVariable Color color) {
        try {
            UUID noteId = UUID.fromString(id);
            NotesDto response = noteService.changeColor(color, noteId);
            return responseHandler.response("Color changed successfully", HttpStatus.OK, response);
        } catch (NoteNotFoundException ex) {
            throw (ex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
