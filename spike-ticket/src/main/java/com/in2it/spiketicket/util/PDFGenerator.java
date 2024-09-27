package com.in2it.spiketicket.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.in2it.spiketicket.entity.Ticket;
import com.in2it.spiketicket.repository.TicketRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Component
public class PDFGenerator {

	@Value("${pdfDir}")
	private String pdfDir;

	@Value("${reportFileName}")
	private String reportFileName;

	@Value("${reportFileNameDateFormat}")
	private String reportFileNameDateFormat;

	@Value("${localDateFormat}")
	private String localDateFormat;

	@Value("${logoImgPath}")
	private String logoImgPath;

	@Value("${pdf-logoImgPath}")
	private String pdf_logoImgPath;

	@Value("${logoImgScale}")
	private Float[] logoImgScale;

	@Value("${pdf-logoImgScale}")
	private Float[] pdf_logoImgScale;

	@Value("${currencySymbol:}")
	private String currencySymbol;

	@Value("${table_noOfColumns}")
	private int noOfColumns;

	@Value("${table.columnNames}")
	private List<String> columnNames;

	@Autowired
	TicketRepository ticketRepository;

	private static Font COURIER = new Font(Font.FontFamily.COURIER, 20, Font.BOLD);
	private static Font COURIER_SMALL = new Font(Font.FontFamily.COURIER, 16, Font.BOLD);
	private static Font COURIER_SMALL_FOOTER = new Font(Font.FontFamily.COURIER, 12, Font.BOLD);

	public void generatePdfReport() {
		File file = new File(pdfDir);
		if (!file.isDirectory()) {
			try {
				Files.createDirectories(Path.of(pdfDir));
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		Document document = new Document();

		try {
			PdfWriter.getInstance(document, new FileOutputStream(getPdfNameWithDate()));
			document.open();
			addLogo(document);
			addDocTitle(document);
			createTable(document, noOfColumns);
			addFooter(document);
			document.close();
			System.out.println("------------------Your PDF Report is ready!-------------------------");

		} catch (FileNotFoundException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

//	private void addLogo(Document document) {
//		try {	
//			Image pdf_img = Image.getInstance(pdf_logoImgPath);
//			pdf_img.scalePercent(pdf_logoImgScale[0], pdf_logoImgScale[1]);
//			pdf_img.setAlignment(Element.ALIGN_LEFT);
//			document.add(pdf_img);
//			
//			
//			Image img = Image.getInstance(logoImgPath);
//			img.scalePercent(logoImgScale[0], logoImgScale[1]);
//			img.setAlignment(Element.ALIGN_RIGHT);
//			document.add(img);
//			
//		} catch (DocumentException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	private void addLogo(Document document) {
		try {
			// Create a table with 2 columns
			PdfPTable table = new PdfPTable(2);
			table.setWidthPercentage(100); // Set table width to 100% of the page

			// Create the first image
			Image pdf_img = Image.getInstance(pdf_logoImgPath);
			pdf_img.scalePercent(pdf_logoImgScale[0], pdf_logoImgScale[1]);
			pdf_img.setAlignment(Element.ALIGN_LEFT);
			PdfPCell cell1 = new PdfPCell(pdf_img);
			cell1.setBorder(PdfPCell.NO_BORDER); // Optional: remove borders
			cell1.setHorizontalAlignment(Element.ALIGN_LEFT); // Align left
			table.addCell(cell1);

			// Create the second image
			Image img = Image.getInstance(logoImgPath);
			img.scalePercent(logoImgScale[0], logoImgScale[1]);
			img.setAlignment(Element.ALIGN_RIGHT);
			PdfPCell cell2 = new PdfPCell(img);
			cell2.setBorder(PdfPCell.NO_BORDER); // Optional: remove borders
			cell2.setHorizontalAlignment(Element.ALIGN_RIGHT); // Align right
			table.addCell(cell2);

			// Add the table to the document
			document.add(table);

		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
	}

	private void addDocTitle(Document document) throws DocumentException {
		String localDateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern(localDateFormat));
		Paragraph p1 = new Paragraph();
		leaveEmptyLine(p1, 1);
		p1.add(new Paragraph(reportFileName, COURIER));
		p1.setAlignment(Element.ALIGN_CENTER);
		leaveEmptyLine(p1, 1);
		p1.add(new Paragraph("Report generated on " + localDateString, COURIER_SMALL));

		document.add(p1);

	}

	private void createTable(Document document, int noOfColumns) throws DocumentException {
		Paragraph paragraph = new Paragraph();
		leaveEmptyLine(paragraph, 3);
		document.add(paragraph);

		PdfPTable table = new PdfPTable(noOfColumns);

		for (int i = 0; i < noOfColumns; i++) {
			PdfPCell cell = new PdfPCell(new Phrase(columnNames.get(i)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(BaseColor.CYAN);
			table.addCell(cell);
		}

		table.setHeaderRows(1);
		getDbData(table);
		document.add(table);
	}

	private void getDbData(PdfPTable table) {

		List<Ticket> tickets = ticketRepository.findAll();
		for (Ticket ticket : tickets) {

			table.setWidthPercentage(100);
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

			table.addCell(String.valueOf(ticket.getId()));
			table.addCell(ticket.getTitle());
			table.addCell(ticket.getAssignTo());
			table.addCell(ticket.getStatus().toString());
			table.addCell(ticket.getCreatedAt().toString());

			System.out.println(ticket.getAssignedBy());
		}

	}

	private void addFooter(Document document) throws DocumentException {
		Paragraph p2 = new Paragraph();
		leaveEmptyLine(p2, 3);
		p2.setAlignment(Element.ALIGN_MIDDLE);
		p2.add(new Paragraph("------------------------End Of " + reportFileName + "------------------------",
				COURIER_SMALL_FOOTER));

		document.add(p2);
	}

	private static void leaveEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	private String getPdfNameWithDate() {
		String localDateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern(reportFileNameDateFormat));
		return pdfDir + reportFileName + "-" + System.currentTimeMillis() + localDateString + ".pdf";
	}
}