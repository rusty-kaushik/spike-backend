package com.in2it.spiketicket.service;

import java.io.IOException;

import jakarta.servlet.http.HttpServletResponse;

public interface ReportService {
	
	void exportToPdf(HttpServletResponse response) throws IOException;
	void exportToExcel(HttpServletResponse response) throws IOException;

}
