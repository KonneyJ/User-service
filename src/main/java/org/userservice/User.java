package org.userservice;

import java.time.LocalDateTime;

public class User {
    private int id;
    private String name;
    private String email;
    private byte age;
    private LocalDateTime created = LocalDateTime.now();
}
