package com.eventsystem.event_management.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class TicketTest {

    @Test
    void shouldCreateAndVerifyTickets() {
        User user = new User("test@pk.pl", "pass", Role.USER);
        Event event = new Event("Koncert", "Opis", LocalDateTime.now(), 10);

        StandardTicket stdTicket = new StandardTicket(user, event, "TCK-STD");

        assertEquals(user, stdTicket.getUser());
        assertEquals(event, stdTicket.getEvent());
        assertEquals("TCK-STD", stdTicket.getTicketCode());
        assertNotNull(stdTicket.getBookingTime());
        assertEquals(50.0, stdTicket.calculatePrice());

        VipTicket vipTicket = new VipTicket(user, event, "TCK-VIP");
        assertEquals(150.0, vipTicket.calculatePrice());
    }
}