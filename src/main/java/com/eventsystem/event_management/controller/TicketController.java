package com.eventsystem.event_management.controller;

import com.eventsystem.event_management.factory.TicketFactory;
import com.eventsystem.event_management.model.Event;
import com.eventsystem.event_management.model.Ticket;
import com.eventsystem.event_management.model.User;
import com.eventsystem.event_management.repository.EventRepository;
import com.eventsystem.event_management.repository.TicketRepository;
import com.eventsystem.event_management.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tickets")
@SecurityRequirement(name = "basicAuth")
public class TicketController {

    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final TicketFactory ticketFactory;

    public TicketController(TicketRepository ticketRepository, EventRepository eventRepository, UserRepository userRepository, TicketFactory ticketFactory) {
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.ticketFactory = ticketFactory;
    }

    @GetMapping("/all")
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }


    @PostMapping("/book")
    public ResponseEntity<?> bookTicket(@RequestParam Long eventId, @RequestParam String email, @RequestParam String ticketType) {
        Event event = eventRepository.findById(eventId).orElse(null);
        User user = userRepository.findByEmail(email).orElse(null);

        if (event == null || user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nie znaleziono wydarzenia lub użytkownika.");
        }

        String ticketCode = "TICKET-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        try {
            Ticket ticket = ticketFactory.createTicket(ticketType, user, event, ticketCode);
            Ticket savedTicket = ticketRepository.save(ticket);
            return new ResponseEntity<>(savedTicket, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}