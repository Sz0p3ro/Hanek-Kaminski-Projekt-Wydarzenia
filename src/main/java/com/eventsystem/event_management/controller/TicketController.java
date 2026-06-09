package com.eventsystem.event_management.controller;

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

    public TicketController(TicketRepository ticketRepository, EventRepository eventRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/all")
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }


    @PostMapping("/book")
    public ResponseEntity<?> bookTicket(@RequestParam Long eventId, @RequestParam String email) {
        Event event = eventRepository.findById(eventId).orElse(null);

        // Szybkie zapytanie po indeksie (wykorzystuje Twoją metodę z interfejsu)
        User user = userRepository.findByEmail(email).orElse(null);

        if (event == null || user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nie znaleziono wydarzenia lub użytkownika.");
        }

        String ticketCode = "TICKET-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Ticket ticket = new Ticket(user, event, ticketCode);
        Ticket savedTicket = ticketRepository.save(ticket);

        return new ResponseEntity<>(savedTicket, HttpStatus.CREATED);
    }
}