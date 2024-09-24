package com.spike.calender.dto;

import org.springframework.stereotype.Component;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {

    @NotNull
    @Size(min = 3, message = "Please, set an email address to send the mail")
    private String to;
    @NotNull
    @Size(min = 2, message = "Please, set some headings to send the mail")
    private String subject;
    @Size(min = 2, message = "Please, set some messages to send the mail")
    @NotNull
    private String message;
    @NotNull
    @Size(min = 1, message = "Please, set an email address to send the mail")
    private String[] ccs;
}
