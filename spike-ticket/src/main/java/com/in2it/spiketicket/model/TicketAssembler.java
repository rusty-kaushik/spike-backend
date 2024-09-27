package com.in2it.spiketicket.model;


import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.in2it.spiketicket.controller.TicketController;
import com.in2it.spiketicket.dto.TicketDto;


@Component
public class TicketAssembler extends  RepresentationModelAssemblerSupport<TicketDto, TicketModel>{

	public TicketAssembler() {
		super(TicketController.class, TicketModel.class);
		
	}

	@Override
	public TicketModel toModel(TicketDto entity) {
		TicketModel model = new TicketModel();
		BeanUtils.copyProperties(entity, model);
	
		return model;
		
	}

}
