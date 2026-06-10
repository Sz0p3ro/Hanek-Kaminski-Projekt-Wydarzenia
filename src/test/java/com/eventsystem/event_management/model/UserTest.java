package com.eventsystem.event_management.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void shouldCreateUserAndSetProperties() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@event.pl");
        user.setPassword("haslo123");
        user.setRole(Role.USER);

        assertEquals(1L, user.getId());
        assertEquals("test@event.pl", user.getEmail());
        assertEquals("haslo123", user.getPassword());
        assertEquals(Role.USER, user.getRole());
    }

    @Test
    void shouldCreateUserWithConstructor() {
        User user = new User("admin@event.pl", "admin1", Role.ADMIN);
        assertEquals("admin@event.pl", user.getEmail());
        assertEquals(Role.ADMIN, user.getRole());
    }
}