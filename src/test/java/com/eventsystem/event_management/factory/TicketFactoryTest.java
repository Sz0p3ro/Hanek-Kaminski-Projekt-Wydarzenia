package com.eventsystem.event_management.factory;

import com.eventsystem.event_management.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TicketFactoryTest {

    private TicketFactory ticketFactory;
    private User testUser;
    private Event testEvent;

    @BeforeEach
    void setUp() {
        ticketFactory = new TicketFactory();
        testUser = new User("user@event.pl", "password", Role.USER);
        testEvent = new Event("Juwenalia", "Opis", LocalDateTime.now().plusDays(5), 100);
    }

    @Test
    void shouldCreateVipTicketWithCorrectPrice() {
        Ticket ticket = ticketFactory.createTicket("VIP", testUser, testEvent, "TCK-VIP-123");

        assertTrue(ticket instanceof VipTicket);
        assertEquals(150.0, ticket.calculatePrice());
        assertEquals("TCK-VIP-123", ticket.getTicketCode());
    }

    @Test
    void shouldCreateStandardTicketWithCorrectPrice() {
        Ticket ticket = ticketFactory.createTicket("STANDARD", testUser, testEvent, "TCK-STD-123");

        assertTrue(ticket instanceof StandardTicket);
        assertEquals(50.0, ticket.calculatePrice());
    }

    @Test
    void shouldThrowExceptionWhenTicketTypeIsInvalid() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ticketFactory.createTicket("ULGOWY", testUser, testEvent, "TCK-ERR");
        });

        assertEquals("Nieprawidłowy typ biletu: ULGOWY", exception.getMessage());
    }
}