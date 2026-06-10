CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE events (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    date_time TIMESTAMP NOT NULL,
    max_capacity INTEGER NOT NULL,
    sold_tickets INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE tickets (
    id BIGSERIAL PRIMARY KEY,
    ticket_code VARCHAR(255) NOT NULL,
    booking_time TIMESTAMP NOT NULL,
    user_id BIGINT NOT NULL,
    event_id BIGINT NOT NULL,
    dtype VARCHAR(31) NOT NULL,
    CONSTRAINT fk_tickets_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_tickets_event FOREIGN KEY (event_id) REFERENCES events(id)
);
