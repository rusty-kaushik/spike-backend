package com.in2it.spiketicket.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.in2it.spiketicket.constants.Status;
import com.in2it.spiketicket.dto.CreateTicketDto;
import com.in2it.spiketicket.dto.TicketDto;
import com.in2it.spiketicket.entity.Ticket;
import com.in2it.spiketicket.repository.TicketRepository;
import com.in2it.spiketicket.search.TicketSpecification;
import com.in2it.spiketicket.service.TicketService;
import com.in2it.spiketicket.service.exception.HierarchyException;
import com.in2it.spiketicket.service.exception.StatusNotFoundException;
import com.in2it.spiketicket.service.exception.TicketNotFoundException;
import com.in2it.spiketicket.util.PDFGenerator;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	TicketRepository repository;
	
	@Autowired
	PDFGenerator pdfGenerator;

	@Autowired
	ModelMapper mapper;

	@Override
	public TicketDto raiseTicket(CreateTicketDto dto) {

		if (dto != null) {
			Ticket ticket = repository.save(Ticket.builder().assignedBy(dto.getAssignedBy()).title(dto.getTitle())
					.description(dto.getDescription()).assignTo(dto.getAssignTo()).createdAt(LocalDate.now())
					.status(Status.OPEN).deleted(false).build());
			return mapper.map(ticket, TicketDto.class);
		}
		return null;
	}

	@Override
	public Page<TicketDto> getAllTicket(int page, int size, List<String> sortList, String sortOrder) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortOrder)));

		Page<Ticket> pageOfTickets = repository.findByDeleted(false, pageable);

		List<TicketDto> ticketDtos = pageOfTickets.getContent().stream()
				.map(ticket -> mapper.map(ticket, TicketDto.class)).collect(Collectors.toList());
		return new PageImpl<>(ticketDtos, pageable, pageOfTickets.getTotalElements());
	}

	@Override
	public long getCountOfTotalTicket() {

		return repository.countByDeleted(false);
	}

	@Override
	public long getCountByStatus(String status) {
		return repository.countByStatusAndDeleted(Status.valueOf(status.toUpperCase()), false);
	}

	@Override
	public TicketDto updateStatusOfTicket(long id, String status, String userName) {
		Ticket ticket = repository.findByIdAndDeleted(id, false)
				.orElseThrow(() -> new TicketNotFoundException("Ticket dosen't exist with given Id"));
		if (status.equalsIgnoreCase("OPEN") || status.equalsIgnoreCase("CLOSED")) {

			Status newStatus = Status.valueOf(status);
			if (newStatus.ordinal() > ticket.getStatus().ordinal()) {
				ticket.setStatus(Status.valueOf(status));
				ticket.setUpdatedBy(userName);
				ticket.setUpdatedAt(LocalDate.now());
				repository.save(ticket);
				return mapper.map(ticket, TicketDto.class);
			}else if(newStatus.ordinal() == ticket.getStatus().ordinal()) {
				throw new HierarchyException("Ticket is allready on this status.");
			} else {
				throw new HierarchyException("You can not go backward");
			}

		} else {
			throw new StatusNotFoundException("Status dos't exist with given name ");
		}

	}

	@Override
	public TicketDto updateStatusOfTicket(long id, Status status, String userName) {
		Ticket ticket = repository.findByIdAndDeleted(id, false)
				.orElseThrow(() -> new TicketNotFoundException("Ticket dosen't exist with given Id"));
		
		if (status.ordinal() > ticket.getStatus().ordinal()) {
			ticket.setStatus(status);
			ticket.setUpdatedBy(userName);
			ticket.setUpdatedAt(LocalDate.now());
			
			repository.save(ticket);
			return mapper.map(ticket, TicketDto.class);
		}else if(status.ordinal() == ticket.getStatus().ordinal()) {
			throw new HierarchyException("Ticket is allready on this status.");
		}
		else {
			throw new HierarchyException("You can not go backward");
		}

	}

	@Override
	public boolean deleteTicket(long ticketId, String userName) {
		Ticket ticket = repository.findByIdAndDeleted(ticketId, false)
				.orElseThrow(() -> new TicketNotFoundException("Ticket dosen't exist with given Id"));
		ticket.setDeleted(true);
		ticket.setUpdatedBy(userName);
		ticket.setUpdatedAt(LocalDate.now());
		repository.save(ticket);

		return true;
	}

	@Override
	public Page<TicketDto> searchTickets(String keyword, Long id, LocalDate createdAt, int page, int size,
			List<String> sortList, String sortOrder) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortOrder)));

		Specification<Ticket> spec = TicketSpecification.search(keyword, id, createdAt);
		Page<Ticket> ticketPage = repository.findAll(spec, pageable);

		List<TicketDto> ticketDtos = ticketPage.getContent().stream().map(ticket -> mapper.map(ticket, TicketDto.class))
				.collect(Collectors.toList());

		return new PageImpl<>(ticketDtos, pageable, ticketPage.getTotalElements());
	}

	@Override
	public Page<TicketDto> searchTicketsByText(String keyword, int page, int size, List<String> sortList,
			String sortOrder) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortOrder)));

		Page<Ticket> ticketPage = null;
		if (keyword == null) {
			ticketPage = repository.findAll(pageable);
		} else {
			ticketPage = repository.fullTextSearch(keyword, pageable);

		}

		List<TicketDto> ticketDtos = ticketPage.getContent().stream().map(ticket -> mapper.map(ticket, TicketDto.class))
				.collect(Collectors.toList());

		return new PageImpl<>(ticketDtos, pageable, ticketPage.getTotalElements());

	}
	
	@Override
	public List<TicketDto> getAllTickets() {
		List<Ticket> all = repository.findAll();
		return all.stream().map(ticket-> mapper.map(ticket, TicketDto.class)).collect(Collectors.toList());
	
	}

//	=====================================================================================================================================
	private List<Sort.Order> createSortOrder(List<String> sortList, String sortDirection) {
		List<Sort.Order> sorts = new ArrayList<>();
		Sort.Direction direction;
		if (sortList != null) {
			for (String sort : sortList) {
				if (sortDirection != null) {
					direction = Sort.Direction.fromString(sortDirection);
				} else {
					direction = Sort.Direction.DESC;
				}
				sorts.add(new Sort.Order(direction, sort));
			}
		}
		return sorts;
	}

	

}
