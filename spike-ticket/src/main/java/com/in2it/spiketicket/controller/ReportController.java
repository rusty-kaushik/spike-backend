package com.in2it.spiketicket.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.in2it.spiketicket.service.ReportService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService; 
    
    @GetMapping("/export/pdf")
    @Operation(summary = "Export tickets to PDF", description = "Generates a PDF file containing all tickets and prompts the user to download it.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful export of tickets to PDF"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public void exportToPdf(HttpServletResponse response) throws IOException {
//        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=tickets.pdf");

        reportService.exportToPdf(response);
    }

    
    @GetMapping("/export/excel")
    @Operation(summary = "Export tickets to Excel")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful export of tickets to Excel"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public void exportToExcel(HttpServletResponse response) throws IOException {
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=tickets.xlsx");

        reportService.exportToExcel(response);
    }
}
