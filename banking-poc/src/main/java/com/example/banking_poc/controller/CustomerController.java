package com.example.banking_poc.controller;

import com.example.banking_poc.dto.CustomerRequest;
import com.example.banking_poc.dto.CustomerResponse;
import com.example.banking_poc.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Create customer
    @Operation(
            summary = "Create a new customer",
            description = "Creates a new banking customer."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Customer created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid customer data"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Customer number or email already exists"
            )
    })
    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(
            @Valid @RequestBody CustomerRequest request) {

        CustomerResponse response = customerService.createCustomer(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // Get all customers
    @Operation(
            summary = "Get all customers",
            description = "Returns a list of all banking customers."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Customers retrieved successfully"
    )
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {

        List<CustomerResponse> responses =
                customerService.getAllCustomers();

        return ResponseEntity.ok(responses);
    }

    // Search customers
    @Operation(
            summary = "Search customers",
            description = "Searches customers by first name or last name."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Search completed successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Search parameter is missing"
            )
    })
    @GetMapping("/search")
    public ResponseEntity<List<CustomerResponse>> searchCustomers(
            @Parameter(
                    description = "Name to search for in first name or last name",
                    example = "John"
            )
            @RequestParam String name) {

        List<CustomerResponse> responses =
                customerService.searchCustomers(name);

        return ResponseEntity.ok(responses);
    }

    // Get customer by ID
    @Operation(
            summary = "Get customer by ID",
            description = "Returns a single customer using the customer ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Customer retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Customer not found"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(
            @Parameter(
                    description = "Unique ID of the customer",
                    example = "1"
            )
            @PathVariable Long id) {

        CustomerResponse response =
                customerService.getCustomerById(id);

        return ResponseEntity.ok(response);
    }

    // Update customer
    @Operation(
            summary = "Update customer",
            description = "Updates an existing customer's information."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Customer updated successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid customer data"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Customer not found"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Customer number or email already exists"
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @Parameter(
                    description = "Unique ID of the customer",
                    example = "1"
            )
            @PathVariable Long id,
            @Valid @RequestBody CustomerRequest request) {

        CustomerResponse response =
                customerService.updateCustomer(id, request);

        return ResponseEntity.ok(response);
    }

    // Delete customer
    @Operation(
            summary = "Delete customer",
            description = "Deletes an existing customer using the customer ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Customer deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Customer not found"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(
            @Parameter(
                    description = "Unique ID of the customer",
                    example = "1"
            )
            @PathVariable Long id) {

        customerService.deleteCustomer(id);

        return ResponseEntity.noContent().build();
    }
}