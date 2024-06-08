package com.example.locking.service;


import com.example.locking.entity.BusDetails;
import com.example.locking.entity.Ticket;
import com.example.locking.repository.BusRepository;
import com.example.locking.repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingService {

	@Autowired
	private BusRepository busRepository;

	@Autowired
	private TicketRepository ticketRepository;

	private void saveTicket(String firstName, String lastName, String gender, BusDetails busDetails) throws SeatNotAvailable {
		
		if (busDetails.getCapacity() <= busDetails.getTickets().size()) {
			throw new SeatNotAvailable();
		}

		Ticket ticket = new Ticket();
		ticket.setFirstName(firstName);
		ticket.setLastName(lastName);
		ticket.setGender(gender);

		busDetails.addTicket(ticket);

		ticketRepository.save(ticket);
	}

	@Transactional
	public void bookTicket() throws SeatNotAvailable, InterruptedException {
		Optional<BusDetails> busOptional = busRepository.findWithLockingById(1L);

		if (busOptional.isPresent()) {
			saveTicket("John", "Allen", "Male", busOptional.get());
		}
		
		Thread.sleep(1000);
	}
	
	@Transactional
	public void bookTicket1() throws SeatNotAvailable, InterruptedException {
		Optional<BusDetails> busOptional = busRepository.findWithLockingById(1L);

		if (busOptional.isPresent()) {
			saveTicket("Maira", "Allen", "Female", busOptional.get());
		}
		
		Thread.sleep(1000);
	}
}
