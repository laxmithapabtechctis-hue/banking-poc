package com.example.banking_poc.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(
        name = "CustomerResponse",
        description = "Response object containing banking customer information"
)
public class CustomerResponse {

    @Schema(
            description = "Database-generated unique customer ID",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Unique customer number",
            example = "CUST1001"
    )
    private String customerNumber;

    @Schema(
            description = "Customer first name",
            example = "John"
    )
    private String firstName;

    @Schema(
            description = "Customer last name",
            example = "Doe"
    )
    private String lastName;

    @Schema(
            description = "Customer email address",
            example = "john.doe@example.com"
    )
    private String email;

    @Schema(
            description = "Customer phone number",
            example = "9876543210"
    )
    private String phoneNumber;

    @Schema(
            description = "Customer residential address",
            example = "123 Main Street, New Delhi"
    )
    private String address;

    @Schema(
            description = "Customer date of birth",
            example = "1995-06-15"
    )
    private LocalDate dateOfBirth;

    @Schema(
            description = "Date and time when the customer was created",
            example = "2026-09-04T10:30:00"
    )
    private LocalDateTime createdAt;

    @Schema(
            description = "Date and time when the customer was last updated",
            example = "2026-09-04T10:30:00"
    )
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}