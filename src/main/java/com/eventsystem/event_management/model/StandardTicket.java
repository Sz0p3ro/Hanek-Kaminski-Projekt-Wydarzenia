package com.eventsystem.event_management.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("STANDARD")
public class StandardTicket extends Ticket {
    public StandardTicket() {}

    public StandardTicket(User user, Event event, String ticketCode) {
        super(user, event, ticketCode);
    }

    @Override
    public double calculatePrice() {
        return 50.0; // Cena standardowa
    }
}
