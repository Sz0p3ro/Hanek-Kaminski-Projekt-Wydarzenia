package com.eventsystem.event_management.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tytuł wydarzenia nie może być pusty")
    @Column(nullable = false)
    private String title;

    private String description;

    @Future(message = "Data wydarzenia musi być w przyszłości")
    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Min(value = 1, message = "Wydarzenie musi mieć co najmniej 1 miejsce")
    @Column(nullable = false)
    private Integer maxCapacity;

    @Column(nullable = false)
    private Integer soldTickets = 0;

    public Event() {}

    public Event(String title, String description, LocalDateTime dateTime) {
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.maxCapacity = maxCapacity;
        this.soldTickets = 0;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

    public Integer getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(Integer maxCapacity) { this.maxCapacity = maxCapacity; }

    public Integer getSoldTickets() { return soldTickets; }
    public void setSoldTickets(Integer soldTickets) { this.soldTickets = soldTickets; }

    public boolean hasAvailableSeats() {
        return maxCapacity > soldTickets;
    }
}