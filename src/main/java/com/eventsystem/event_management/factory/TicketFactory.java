package com.eventsystem.event_management.factory;

import com.eventsystem.event_management.model.*;
import org.springframework.stereotype.Component;

@Component
public class TicketFactory {
    public Ticket createTicket(String type, User user, Event event, String ticketCode) {
        if ("VIP".equalsIgnoreCase(type)) {
            return new VipTicket(user, event, ticketCode);
        } else if ("STANDARD".equalsIgnoreCase(type)) {
            return new StandardTicket(user, event, ticketCode);
        }

        throw new IllegalArgumentException("Nieprawidłowy typ biletu: " + type);
    }
}