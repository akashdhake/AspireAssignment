package com.aspire.model;

import com.aspire.constants.UserRole;
import lombok.Data;

import java.util.concurrent.atomic.AtomicLong;

@Data
public class User {

    private long id;
    private String name;
    private UserRole role;

    private static final AtomicLong idCounter = new AtomicLong(1);

    public User(String name, UserRole role) {
        this.id = generateCustomId();
        this.name = name;
        this.role = role;
    }

    private long generateCustomId() {
        return idCounter.getAndIncrement();
    }
}
