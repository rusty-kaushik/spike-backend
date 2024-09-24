package com.mail.JavaMail.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class EmailHistory {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
    @NotNull
    @Size(min = 1, message = "Please, set an email address to send the message to it")
    private String recipient;
    @NotNull
    @Size(min = 1, message = "Please, set an email address to send the message to it")
    private String[] ccs;
    @NotNull
    private String subject;
    
    @NotNull
    @Size(min = 2,max =4000, message = "Please, set some messages to send the mail")
    private String message;
    @NotNull
    private String sender;
    @NotNull
    private LocalDateTime createdAt;
    @NotNull
    private boolean deletedFromSender;
    @NotNull
    private boolean deletedFromReciever;

    private List<String> attachmentNewNames;
    
    private List<String> attachmentOldNames;
    
    private List<String> attachmentPaths;

    
}
