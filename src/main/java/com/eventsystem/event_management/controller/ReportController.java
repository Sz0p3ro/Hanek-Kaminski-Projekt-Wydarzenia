package com.eventsystem.event_management.controller;

import com.eventsystem.event_management.model.Event;
import com.eventsystem.event_management.repository.EventRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reports")
@SecurityRequirement(name = "basicAuth")
public class ReportController {

    private final EventRepository eventRepository;

    public ReportController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping("/popularity")
    public ResponseEntity<List<Map<String, Object>>> getEventPopularityReport() {
        List<Event> events = eventRepository.findAll();

        List<Map<String, Object>> report = events.stream().map(event -> {
                    Map<String, Object> eventData = new HashMap<>();
                    eventData.put("eventId", event.getId());
                    eventData.put("title", event.getTitle());
                    eventData.put("maxCapacity", event.getMaxCapacity());
                    eventData.put("soldTickets", event.getSoldTickets());

                    double popularity = 0.0;
                    if (event.getMaxCapacity() > 0) {
                        popularity = ((double) event.getSoldTickets() / event.getMaxCapacity()) * 100;
                    }
                    eventData.put("popularityPercentage", Math.round(popularity * 100.0) / 100.0 + "%");
                    return eventData;
                })
                .sorted((m1, m2) -> Integer.compare((Integer) m2.get("soldTickets"), (Integer) m1.get("soldTickets")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(report);
    }
}