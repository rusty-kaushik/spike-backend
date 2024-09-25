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

    private String recipientMailId;
    private String heading;
    private String body;
    private String[] sharedBy;
}
