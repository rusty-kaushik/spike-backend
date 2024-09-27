package com.in2it.spiketicket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.in2it.spiketicket.util.PDFGenerator;

@RestController
@RequestMapping("/tickets")
public class PDFController {

    @Autowired
    private PDFGenerator pdfGenerator;

    @GetMapping("/export/pdf")
    public ResponseEntity<String> generatePdfReport() {
        try {
            pdfGenerator.generatePdfReport();
            return ResponseEntity.ok("PDF report generated successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error generating PDF report: " + e.getMessage());
        }
    }
}