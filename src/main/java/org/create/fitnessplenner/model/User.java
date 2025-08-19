package org.create.fitnessplenner.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Username cannot be empty or contain only whitespace")
    @Size(min = 5, max = 15, message = "The username must contain between 5 and 15 characters")
    private String username;

    @Size(max = 20, message = "Name can be up to 20 characters long")
    private String name;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,50}$",
            message = "Password must contain uppercase, lowercase, digit, and special character")
    private String password;


    private String gender;

    private LocalDate dateOfBirth;

    private double height; // w cm
    private double weight; // w kg

    private String fitnessGoal; // np. "Lose weight", "Build muscle", "Stay active"




}

