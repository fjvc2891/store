package com.store.blossom.model;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Admin extends User {
    private String role;
}
