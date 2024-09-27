package com.in2it.spiketicket.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.in2it.spiketicket.dto.TicketDto;
import com.in2it.spiketicket.service.ReportService;
import com.in2it.spiketicket.service.TicketService;
import com.in2it.spiketicket.util.TicketExportToExcelService;
import com.in2it.spiketicket.util.TicketExportToPdfService;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	TicketService ticketService;
	
	@Autowired
	TicketExportToExcelService excelService;
	
	@Autowired
	TicketExportToPdfService pdfService;

	@Override
	public void exportToPdf(HttpServletResponse response) throws IOException {
		List<TicketDto> allTickets = ticketService.getAllTickets();
		pdfService.exportToPDF(response, allTickets);
		
	}

	@Override
	public void exportToExcel(HttpServletResponse response) throws IOException {
		List<TicketDto> allTickets = ticketService.getAllTickets();
		excelService.exportToExcel(response, allTickets);
	}

}
