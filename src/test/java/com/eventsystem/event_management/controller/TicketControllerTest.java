package com.eventsystem.event_management.controller;

import com.eventsystem.event_management.factory.TicketFactory;
import com.eventsystem.event_management.model.*;
import com.eventsystem.event_management.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TicketControllerTest {

    private MockMvc mockMvc;

    @Mock private TicketRepository ticketRepository;
    @Mock private EventRepository eventRepository;
    @Mock private UserRepository userRepository;
    @Mock private TicketFactory ticketFactory;

    @InjectMocks
    private TicketController ticketController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ticketController).build();
    }

    @Test
    void shouldGetAllTickets() throws Exception {
        when(ticketRepository.findAll()).thenReturn(List.of());
        mockMvc.perform(get("/api/tickets/all"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnNotFoundWhenEventOrUserDoesNotExist() throws Exception {
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/tickets/book")
                        .param("eventId", "1")
                        .param("email", "test@event.pl")
                        .param("ticketType", "STANDARD"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnConflictWhenNoAvailableSeats() throws Exception {
        Event fullEvent = new Event("Test", "Opis", LocalDateTime.now().plusDays(1), 1);
        fullEvent.setSoldTickets(1);
        User user = new User("test@event.pl", "pass", Role.USER);

        when(eventRepository.findById(1L)).thenReturn(Optional.of(fullEvent));
        when(userRepository.findByEmail("test@event.pl")).thenReturn(Optional.of(user));

        mockMvc.perform(post("/api/tickets/book")
                        .param("eventId", "1")
                        .param("email", "test@event.pl")
                        .param("ticketType", "STANDARD"))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldBookTicketSuccessfully() throws Exception {
        Event event = new Event("Test", "Opis", LocalDateTime.now().plusDays(1), 10);
        event.setId(1L);
        User user = new User("test@event.pl", "pass", Role.USER);

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(userRepository.findByEmail("test@event.pl")).thenReturn(Optional.of(user));

        StandardTicket mockTicket = new StandardTicket(user, event, "TCK-123");
        when(ticketFactory.createTicket(anyString(), any(User.class), any(Event.class), anyString())).thenReturn(mockTicket);
        when(ticketRepository.save(any())).thenReturn(mockTicket);

        mockMvc.perform(post("/api/tickets/book")
                        .param("eventId", "1")
                        .param("email", "test@event.pl")
                        .param("ticketType", "STANDARD"))
                .andExpect(status().isCreated());
    }
}