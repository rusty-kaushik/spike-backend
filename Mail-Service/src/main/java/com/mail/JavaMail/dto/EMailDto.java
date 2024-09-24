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
    @Size(min = 9, message = "Please, set an email address to send the mail")
    private String to;
    @NotNull
    @Size(min = 2, message = "Please, set some headings to send the mail")
    private String subject;
    @Size(min = 2, max = 4000, message = "Please, set some messages to send the mail")
    @NotNull
    private String message;
    @NotNull
    @Size(min = 1, message = "Please, set an email address to send the mail")
    private String[] ccs;

}
