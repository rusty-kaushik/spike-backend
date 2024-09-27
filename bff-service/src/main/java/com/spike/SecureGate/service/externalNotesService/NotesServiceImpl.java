package com.spike.SecureGate.service.externalNotesService;

import com.spike.SecureGate.DTO.notsDto.NoteUpdateDto;
import com.spike.SecureGate.enums.NotesColors;
import com.spike.SecureGate.exceptions.UnexpectedException;
import com.spike.SecureGate.feignClients.NoteFeignClient;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class NotesServiceImpl implements NotesService {

    @Autowired
    private NoteFeignClient notesFeignClient;

    private static final Logger logger = LoggerFactory.getLogger(NotesServiceImpl.class);

    @Override
    public ResponseEntity<Object> createNote(Long id) {
        try{
            return notesFeignClient.createNotes(id);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("Error occurred while creating a note: " + e.getMessage());
            throw new UnexpectedException( "UnexpectedError","An unexpected error occurred while creating a note: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> fetchAllNotes(Long userId, String content) {
        try{
            return notesFeignClient.getNotes(content, userId);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("Error occurred while fetching all note: " + e.getMessage());
            throw new UnexpectedException( "UnexpectedError","An unexpected error occurred while fetching all note: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> deleteNotes(String id, long userId){
        try{
            return notesFeignClient.deleteNote(id, userId);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("Error occurred while deleting a note: " + e.getMessage());
            throw new UnexpectedException( "UnexpectedError","An unexpected error occurred while deleting a note: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> editNotes(String id, NoteUpdateDto content) {
        try{
            return notesFeignClient.EditNote(id, content);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("Error occurred while editing a note: " + e.getMessage());
            throw new UnexpectedException( "UnexpectedError","An unexpected error occurred while editing a note: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> editNotesColor(String id, NotesColors color) {
        try{
            return notesFeignClient.ChangeColorOfNote(id, color);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("Error occurred while editing the color of a note: " + e.getMessage());
            throw new UnexpectedException( "UnexpectedError","An unexpected error occurred while editing the color of a note: " + e.getMessage());
        }
    }


}
