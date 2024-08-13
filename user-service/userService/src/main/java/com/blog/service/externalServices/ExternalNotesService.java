package com.blog.service.externalServices;

import com.blog.repository.DTO.notes.NoteRequestDto;
import com.blog.repository.DTO.notes.NoteResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "NotesService", url = "http://localhost:8282")
public interface ExternalNotesService {

    // api to create a new note
    @PostMapping(path = "/spike/notes/create")
    NoteResponseDto create(@RequestParam("creator") String creator,
                           @RequestBody NoteRequestDto noteRequestDto);

    // api to update a note
    @PutMapping(path = "/spike/notes/update")
    NoteResponseDto update(@RequestParam("creator") String creator,
                           @RequestParam("noteId") Long noteId,
                           @RequestBody NoteRequestDto noteRequestDto);

    // api to delete a api
    @DeleteMapping(path = "/spike/notes/delete/{noteId}")
    String delete(@RequestParam("creator") String creator,
                           @PathVariable("noteId") Long noteId);

    // api to fetch notes
    @GetMapping(path = "/spike/notes/fetch")
    Map<String, Object> fetch(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(required = false) String sortColumn,
                              @RequestParam(required = false) String sort,
                              @RequestParam(required = false) String keyword,
                              @RequestParam("creator") String creator);

}
