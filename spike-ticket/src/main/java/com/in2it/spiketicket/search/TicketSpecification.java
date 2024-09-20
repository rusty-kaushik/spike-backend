package com.in2it.spiketicket.search;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.in2it.spiketicket.entity.Ticket;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class TicketSpecification {
    	
	public static Specification<Ticket> search(String keyword, Long id, LocalDate createdAt) {
        return (Root<Ticket> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();

            
            if (id != null) {
                predicate = cb.and(predicate, cb.equal(root.get("id"), id));
            }

           
            if (keyword != null && !keyword.trim().isEmpty()) {
                String lowerKeyword = keyword.toLowerCase();
                Predicate keywordPredicate = cb.or(
                    cb.like(cb.lower(root.get("title")), "%" + lowerKeyword + "%"),
                    cb.like(cb.lower(root.get("assignTo")), "%" + lowerKeyword + "%"),
                    cb.like(cb.lower(root.get("assignedBy")), "%" + lowerKeyword + "%"),
                    cb.like(cb.lower(root.get("status")), "%" + lowerKeyword + "%")
                );
                predicate = cb.and(predicate, keywordPredicate);
            }

           
            if (createdAt != null) {
                predicate = cb.and(predicate, cb.equal(root.get("createdAt"), createdAt));
            }

           
            predicate = cb.and(predicate, cb.isFalse(root.get("deleted")));

            return predicate;
        };
    }
}