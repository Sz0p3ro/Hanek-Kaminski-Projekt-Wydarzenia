package com.eventsystem.event_management.controller;

import com.eventsystem.event_management.model.Event;
import com.eventsystem.event_management.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReportController.class)
public class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventRepository eventRepository;

    @TestConfiguration
    static class TestSecurityConfig {
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http.csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
            return http.build();
        }
    }

    @Test
    public void shouldReturnPopularityReport() throws Exception {
        Event event = new Event();
        event.setTitle("Koncert");
        event.setMaxCapacity(100);
        event.setSoldTickets(50);
        event.setDateTime(LocalDateTime.now().plusDays(1));

        Mockito.when(eventRepository.findAll()).thenReturn(List.of(event));

        mockMvc.perform(get("/api/reports/popularity"))
                .andExpect(status().isOk());
    }
}