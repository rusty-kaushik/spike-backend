package com.in2it.spiketicket.util;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.in2it.spiketicket.dto.TicketDto;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class TicketExportToPdfService extends ReportAbstract {

    public void writeTableData(PdfPTable table, Object data) {
        List<TicketDto> list = (List<TicketDto>) data;

        // for auto wide by paper  size
        table.setWidthPercentage(100);
        // cell
        PdfPCell cell = new PdfPCell();
//        int number = 0;
        for (TicketDto item : list) {
//            number += 1;
            cell.setPhrase(new Phrase(String.valueOf(item.getId()), getFontContent()));
            table.addCell(cell);

            cell.setPhrase(new Phrase(item.getTitle(), getFontContent()));
            table.addCell(cell);

            cell.setPhrase(new Phrase(item.getAssignTo(), getFontContent()));
            table.addCell(cell);

            cell.setPhrase(new Phrase(item.getStatus(), getFontContent()));
            table.addCell(cell);
            
            cell.setPhrase(new Phrase(item.getCreatedAt().toString(), getFontContent()));
            table.addCell(cell);

            

        }

    }


    public void exportToPDF(HttpServletResponse response, Object data) throws IOException {


        // init respose
        response = initResponseForExportPdf(response, "TICKET");

        // define paper size
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        // start document
        document.open();

        // title
        Paragraph title = new Paragraph("Report Ticket", getFontTitle());
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

        // subtitel
        Paragraph subtitel = new Paragraph("Report Date : "+LocalDate.now(), getFontSubtitle());
        subtitel.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(subtitel);

        enterSpace(document);

        // table header     
        String[] headers = new String[]{"Id", "Title", "Assigned To", "Status", "Date"};
        PdfPTable tableHeader = new PdfPTable(5);
        writeTableHeaderPdf(tableHeader, headers);
        document.add(tableHeader);

        // table content
        PdfPTable tableData = new PdfPTable(5);
        writeTableData(tableData, data);
        document.add(tableData);

        document.close();
    }

}