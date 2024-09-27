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
    @Size(min = 1, message = "Provide a valid email address to send a message on it")
    private String recipientMailId;
    @NotNull
    @Size(min = 1, message = "Provide a valid email address to send a carbon Copy of the message on it")
    private String[] sharedBy;
    @NotNull
    private String heading;
    
    @NotNull
    @Size(min = 2,max =4000, message = "Provide some messages to send the mail")
    private String body;
    @NotNull
    private String senderMailId;
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
