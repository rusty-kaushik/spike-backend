package com.in2it.spiketicket.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.in2it.spiketicket.constants.Status;
import com.in2it.spiketicket.dto.CreateTicketDto;
import com.in2it.spiketicket.dto.TicketDto;
import com.in2it.spiketicket.model.TicketAssembler;
import com.in2it.spiketicket.responce.ResponseStructure;
import com.in2it.spiketicket.service.TicketService;

@RestController
@RequestMapping("/tickets")
@Validated
public class TicketController {

	@Autowired
	TicketService service;

	@Autowired
	TicketAssembler ticketAssembler;

	@Autowired
	PagedResourcesAssembler<TicketDto> pagedResourcesAssembler;

	@PostMapping("/create-ticket")
	public ResponseEntity<ResponseStructure<TicketDto>> raiseTicket(@RequestBody CreateTicketDto dto) {
		System.out.println(dto.toString());
		TicketDto raiseTicket = service.raiseTicket(dto);
		ResponseStructure<TicketDto> responce = new ResponseStructure<TicketDto>(raiseTicket,
				"Ticket has been created sucessfully.....", HttpStatus.CREATED.value());
		return ResponseEntity.status(HttpStatus.CREATED).body(responce);
	}

	@GetMapping("/search")
	public ResponseEntity<ResponseStructure<PagedModel<EntityModel<TicketDto>>>> searchTicket(
			@RequestParam(required = false) Long id, @RequestParam(required = false) String keyword,
			@RequestParam(required = false) LocalDate createdAt, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(required = false) List<String> sortList,
			@RequestParam(defaultValue = "ASC") String sortOrder) {

		Page<TicketDto> ticketPage = service.searchTickets(keyword, id, createdAt, page, size, sortList, sortOrder);
		PagedModel<EntityModel<TicketDto>> pagedModel = pagedResourcesAssembler.toModel(ticketPage);

		ResponseStructure<PagedModel<EntityModel<TicketDto>>> response = new ResponseStructure<>(pagedModel,
				"All tickets of page " + page + " retrieved successfully.", HttpStatus.OK.value());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("get-all")
	public ResponseEntity<ResponseStructure<PagedModel<EntityModel<TicketDto>>>> getAllTicket(
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(required = false) List<String> sortList,
			@RequestParam(defaultValue = "ASC") String sortOrder) {

		Page<TicketDto> ticketPage = service.getAllTicket(page, size, sortList, sortOrder);
		PagedModel<EntityModel<TicketDto>> pagedModel = pagedResourcesAssembler.toModel(ticketPage);

		ResponseStructure<PagedModel<EntityModel<TicketDto>>> response = new ResponseStructure<>(pagedModel,
				"All tickets of page " + page + " retrieved successfully.", HttpStatus.OK.value());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/get-total-count")
	public ResponseEntity<ResponseStructure<Long>> getCountOfTotalTicket() {
		long totalTicket = service.getCountOfTotalTicket();
		ResponseStructure<Long> responseStructure = new ResponseStructure<Long>(totalTicket, "Total tickets",
				HttpStatus.OK.value());
		return ResponseEntity.ok(responseStructure);
	}

	@GetMapping("/get-count-by-status")
	public ResponseEntity<ResponseStructure<Long>> getCountByStatus(String status) {
		long totalTicket = service.getCountByStatus(status);
		ResponseStructure<Long> responseStructure = new ResponseStructure<Long>(totalTicket,
				"Total " + status + " tickets", HttpStatus.OK.value());
		return ResponseEntity.ok(responseStructure);
	}

	@PutMapping("/{id}/status")
	public ResponseEntity<ResponseStructure<TicketDto>> updateTicketStatus(@PathVariable long id,
			@RequestParam String status, @RequestParam String userName) {

		TicketDto updatedTicket = service.updateStatusOfTicket(id, status, userName);

		ResponseStructure<TicketDto> response = new ResponseStructure<TicketDto>(updatedTicket,
				"Ticket status updated successfully.", HttpStatus.OK.value());

		return ResponseEntity.ok(response);
	}

	@PutMapping("update/{id}/status")
	public ResponseEntity<ResponseStructure<TicketDto>> updateTicketStatus(@PathVariable long id,
			@RequestParam Status status, @RequestParam String userName) {

		TicketDto updatedTicket = service.updateStatusOfTicket(id, status, userName);

		ResponseStructure<TicketDto> response = new ResponseStructure<TicketDto>(updatedTicket,
				"Ticket status updated successfully.", HttpStatus.OK.value());

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<Boolean>> deleteTicket(@PathVariable long id,
			@RequestParam String userName) {

		boolean responce = service.deleteTicket(id, userName);

		ResponseStructure<Boolean> response = new ResponseStructure<Boolean>(responce,
				"Ticket has been marked as deleted.", HttpStatus.OK.value());

		return ResponseEntity.ok(response);
	}

	@GetMapping("/search-ticket")
	public ResponseEntity<ResponseStructure<PagedModel<EntityModel<TicketDto>>>> searchTickets(
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(value = "sortList", required = false) List<String> sortList,
			@RequestParam(defaultValue = "asc") String sortOrder) {

		Page<TicketDto> ticketDtos = service.searchTicketsByText(keyword, page, size, sortList, sortOrder);
		PagedModel<EntityModel<TicketDto>> pagedModel = pagedResourcesAssembler.toModel(ticketDtos);
		ResponseStructure<PagedModel<EntityModel<TicketDto>>> response = new ResponseStructure<>(pagedModel,
				"All tickets matched with keyword " + keyword + " retrieved successfully.", HttpStatus.OK.value());

		return ResponseEntity.ok(response);
	}

}
