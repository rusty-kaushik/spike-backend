package com.spike.Notes.NotesDto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.spike.Notes.NotesEntity.Color;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.UUID;

public class NotesDto {

     @Hidden
    private UUID id;

    private String content;

    @Hidden
    private Color color;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate createdAt;

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
