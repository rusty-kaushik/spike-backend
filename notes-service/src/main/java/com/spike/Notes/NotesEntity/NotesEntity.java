package com.spike.Notes.NotesEntity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="notes")
public class NotesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name="userId")
    private Long userId;
    @Column(name="content")
    private String content;
    @Column(name="color")
    @Enumerated(EnumType.STRING)
    private Color color;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private status status;
    @Column(name = "createdAt")
    private LocalDate createdAt;
    @Column(name = "updatedAt")
    private LocalDate updatedAt;

    public NotesEntity() {
    }

    public NotesEntity(UUID id, Long userId, String content,Color color, status status,LocalDate createdAt, LocalDate updatedAt) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.status = status;
        this.color=color;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public status getStatus() {
        return status;
    }

    public void setStatus(status status) {
        this.status = status;
    }
}
