package com.spike.SecureGate.feignClients;

import com.spike.SecureGate.DTO.notsDto.NoteUpdateDto;
import com.spike.SecureGate.enums.NotesColors;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "notesClient",  url = "${spike.service.notes_service}")
public interface NoteFeignClient {

    // CREATE A NOTE
    @PostMapping("/spike/create-note/{userId}")
    ResponseEntity<Object> createNotes(
            @PathVariable Long userId
    );

   // FETCH ALL NOTES OF A USER
   @GetMapping("/spike/get-notes/{userId}")
   ResponseEntity<Object> getNotes(
           @RequestParam String content,
           @PathVariable Long userId
   );

   // DELETE A NOTE
   @DeleteMapping("/spike/delete-note/{id}/{userId}")
   ResponseEntity<Object> deleteNote(
           @PathVariable String id,
           @PathVariable Long userId
   );

   // EDIT A NOTE
   @PutMapping("/spike/edit-note/{id}")
   ResponseEntity<Object> EditNote(
           @PathVariable String id,
           @RequestBody NoteUpdateDto notesDto
   );

   // CHANGE THE COLOR OF A NOTE
    @PutMapping("/spike/change-note-color/{id}/{color}")
    ResponseEntity<Object> ChangeColorOfNote(
            @PathVariable String id,
            @PathVariable NotesColors color
    );
}
