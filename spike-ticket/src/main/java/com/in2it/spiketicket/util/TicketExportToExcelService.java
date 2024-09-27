package com.in2it.spiketicket.util;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

import com.in2it.spiketicket.dto.TicketDto;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class TicketExportToExcelService extends ReportAbstract {

    public void writeTableData(Object data) {
        // data
        List<TicketDto> list = (List<TicketDto>) data;

        // font style content
        CellStyle style = getFontContentExcel();

        // starting write on row
        int startRow = 2;

        // write content
        for (TicketDto ticketDto : list) {
            Row row = sheet.createRow(startRow++);
            int columnCount = 0;
            createCell(row, columnCount++, ticketDto.getId(), style);
            createCell(row, columnCount++, ticketDto.getTitle(), style);
            createCell(row, columnCount++, ticketDto.getAssignTo(), style);
            createCell(row, columnCount++, ticketDto.getStatus().toString(), style);
            createCell(row, columnCount++, ticketDto.getCreatedAt().toString(), style);

        }
    }


    public void exportToExcel(HttpServletResponse response, Object data) throws IOException {
        newReportExcel();

        // response  writer to excel
        response = initResponseForExportExcel(response, "TicketExcel");
        ServletOutputStream outputStream = response.getOutputStream();


        // write sheet, title & header
        String[] headers = new String[]{"Id", "Title", "Assigned To", "Status","Date"};
        writeTableHeaderExcel("Sheet Ticket", "Report Ticket", headers);

        // write content row
        writeTableData(data);

        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
