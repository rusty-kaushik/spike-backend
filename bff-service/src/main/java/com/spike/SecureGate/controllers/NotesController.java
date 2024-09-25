package com.spike.SecureGate.controllers;

import com.spike.SecureGate.DTO.notsDto.NoteUpdateDto;
import com.spike.SecureGate.DTO.taskBoardDto.TaskBoardCreationFeignDTO;
import com.spike.SecureGate.enums.NotesColors;
import com.spike.SecureGate.service.externalNotesService.NotesService;
import com.spike.SecureGate.service.externalTaskBoardService.TaskBoardService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notes")
public class NotesController {

    @Autowired
    private NotesService notesService;

    private static final Logger logger = LoggerFactory.getLogger(NotesController.class);

    // CREATE A NEW TASK
    @Operation(
            summary = "Creates a new note",
            description = "Creates a new note with a default data."
    )
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> createNote(
            @RequestParam Long userId
    ) {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        logger.info("Started creating new User");
        ResponseEntity<Object> notes = notesService.createNote(userId);
        logger.info("Finished creating new User");
        return notes;
    }

    // FETCH ALL NOTES
    @Operation(
            summary = "Fetches all notes",
            description = "Fetch all notes of a user by its id or content as a param."
    )
    @GetMapping("/fetch/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> fetchAllNotes(
            @RequestParam(required = false) String content,
            @PathVariable Long userId
    ) {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        logger.info("Started creating new User");
        ResponseEntity<Object> notes = notesService.fetchAllNotes(userId,content);
        logger.info("Finished creating new User");
        return notes;
    }

    // DELETE A NOTE
    @Operation(
            summary = "Delete a notes",
            description = "Delete a notes of a user by its id."
    )
    @DeleteMapping("/delete/{noteId}/{userID}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> deleteNotes(
            @PathVariable String noteId,
            @PathVariable long userID
    ) {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        logger.info("Started creating new User");
        ResponseEntity<Object> notes = notesService.deleteNotes(noteId,userID);
        logger.info("Finished creating new User");
        return notes;
    }

    // EDIT A NOTE
    @Operation(
            summary = "Edit a notes",
            description = "Edit a notes of a user by its id."
    )
    @PutMapping("/edit/{noteId}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> editNotes(
            @PathVariable String noteId,
            @RequestBody NoteUpdateDto content
    ) {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        logger.info("Started editing new User");
        ResponseEntity<Object> notes = notesService.editNotes(noteId,content);
        logger.info("Finished editing new User");
        return notes;
    }

    // EDIT THE COLOR OF A NOTE
    @Operation(
            summary = "Edit the color of a notes",
            description = "Edit the color of a notes of a user by its id."
    )
    @PutMapping("/color/{noteId}/{color}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> editNotesColor(
            @PathVariable String noteId,
            @PathVariable NotesColors color
    ) {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        logger.info("Started editing new User");
        ResponseEntity<Object> notes = notesService.editNotesColor(noteId,color);
        logger.info("Finished editing new User");
        return notes;
    }

}
