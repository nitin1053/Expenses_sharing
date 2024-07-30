package com.example.expensessharing.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "\"user\"") // Use double quotes to escape the reserved keyword
@Data
public class User {
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Mobile number is mandatory")
    private String mobileNumber;

    @Setter
    @Getter
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Expense> expenses;

    public @NotBlank(message = "Name is mandatory") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name is mandatory") String name) {
        this.name = name;
    }

    public @Email(message = "Email should be valid") @NotBlank(message = "Email is mandatory") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Email should be valid") @NotBlank(message = "Email is mandatory") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Mobile number is mandatory") String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(@NotBlank(message = "Mobile number is mandatory") String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

}
