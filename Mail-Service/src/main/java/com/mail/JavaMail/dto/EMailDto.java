package com.mail.JavaMail.dto;

import org.springframework.stereotype.Component;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Data

public class EMailDto {
	
    @NotNull
    @Size(min = 1, message = "Provide a valid email address to send a message on it")
    private String recipientMailId;
    @NotNull
    @Size(min = 2, message = "Please, set some headings to send the mail")
    private String heading;
    @NotNull
    @Size(min = 2,max =4000, message = "Provide some messages to send the mail")
    private String body;
    @NotNull
    @Size(min = 1, message = "Provide a valid email address to send a carbon Copy of the message on it")
    private String[] sharedBy;

}
