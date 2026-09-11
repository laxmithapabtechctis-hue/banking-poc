package com.example.banking_poc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(
        name = "CustomerRequest",
        description = "Request object used to create or update a banking customer"
)
public class CustomerRequest {

    @Schema(
            description = "Unique customer number",
            example = "CUST1001",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Customer number is required")
    private String customerNumber;

    @Schema(
            description = "Customer first name",
            example = "John",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "First name is required")
    private String firstName;

    @Schema(
            description = "Customer last name",
            example = "Doe",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Schema(
            description = "Customer email address",
            example = "john.doe@example.com",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @Schema(
            description = "Customer phone number",
            example = "9876543210",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @Schema(
            description = "Customer residential address",
            example = "123 Main Street, New Delhi"
    )
    private String address;

    @Schema(
            description = "Customer date of birth",
            example = "1995-06-15",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

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
}