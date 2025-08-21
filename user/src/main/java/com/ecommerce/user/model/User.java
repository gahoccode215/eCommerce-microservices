package com.ecommerce.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Document(collection = "users")
public class User {

    private String id;
    private String firstName;
    private String lastName;
    @Indexed(unique = true)
    private String email;
    private String phone;
    private UserRole role = UserRole.ADMIN;

    private Address address;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
