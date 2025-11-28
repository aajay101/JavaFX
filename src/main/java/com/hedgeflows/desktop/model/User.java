package com.hedgeflows.desktop.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
    private String firstName;
    private String lastName;
    private String email;
    private String password; // Plain text for demo purposes
    private String companyName;

    public User() {
        super();
    }

    public User(UUID id, LocalDateTime createdAt, String firstName, String lastName, String email, String password, String companyName) {
        super(id, createdAt);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.companyName = companyName;
    }

    public User(String firstName, String lastName, String email, String password, String companyName) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.companyName = companyName;
    }
}