package com.eventsystem.event_management.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    @Test
    void shouldVerifyAvailableSeatsCorrectly() {
        Event event = new Event("Koncert", "Opis", LocalDateTime.now().plusDays(2), 3);

        event.setSoldTickets(0);
        assertTrue(event.hasAvailableSeats());

        event.setSoldTickets(2);
        assertTrue(event.hasAvailableSeats());

        event.setSoldTickets(3);
        assertFalse(event.hasAvailableSeats());
    }
}