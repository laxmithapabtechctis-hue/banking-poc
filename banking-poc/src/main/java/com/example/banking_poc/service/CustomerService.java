package com.example.banking_poc.service;

import com.example.banking_poc.dto.CustomerRequest;
import com.example.banking_poc.dto.CustomerResponse;
import com.example.banking_poc.entity.Customer;
import com.example.banking_poc.exception.CustomerNotFoundException;
import com.example.banking_poc.exception.DuplicateCustomerException;
import com.example.banking_poc.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RestClient restClient;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.restClient = RestClient.create();
    }

    // Create customer with duplicate checks
    public CustomerResponse createCustomer(CustomerRequest request) {

        // Check for duplicate customer number
        if (customerRepository
                .findByCustomerNumber(request.getCustomerNumber())
                .isPresent()) {

            throw new DuplicateCustomerException(
                    "Customer number already exists: "
                            + request.getCustomerNumber());
        }

        // Check for duplicate email
        if (customerRepository
                .findByEmail(request.getEmail())
                .isPresent()) {

            throw new DuplicateCustomerException(
                    "Email already exists: "
                            + request.getEmail());
        }

        Customer customer = new Customer();

        customer.setCustomerNumber(request.getCustomerNumber());
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setAddress(request.getAddress());
        customer.setDateOfBirth(request.getDateOfBirth());

        LocalDateTime now = LocalDateTime.now();

        customer.setCreatedAt(now);
        customer.setUpdatedAt(now);

        Customer savedCustomer = customerRepository.save(customer);

        // Prepare request for Notification Service
        Map<String, String> notificationRequest = Map.of(
                "customerNumber", savedCustomer.getCustomerNumber(),
                "email", savedCustomer.getEmail()
        );

        // Call Notification Service through Dapr
        restClient.post()
                .uri("http://localhost:3502/v1.0/invoke/notification-service/method/api/notifications/welcome")
                .body(notificationRequest)
                .retrieve()
                .toBodilessEntity();

        return convertToResponse(savedCustomer);
    }

    // Get all customers
    public List<CustomerResponse> getAllCustomers() {

        List<Customer> customers = customerRepository.findAll();

        List<CustomerResponse> responses = new ArrayList<>();

        for (Customer customer : customers) {
            responses.add(convertToResponse(customer));
        }

        return responses;
    }

    // Search customers by first name or last name
    public List<CustomerResponse> searchCustomers(String name) {

        List<Customer> customers =
                customerRepository
                        .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                                name,
                                name
                        );

        List<CustomerResponse> responses = new ArrayList<>();

        for (Customer customer : customers) {
            responses.add(convertToResponse(customer));
        }

        return responses;
    }

    // Get customer by ID
    public CustomerResponse getCustomerById(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer not found with id: " + id));

        return convertToResponse(customer);
    }

    // Update customer with duplicate checks
    public CustomerResponse updateCustomer(
            Long id,
            CustomerRequest request) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer not found with id: " + id));

        // Check customer number
        customerRepository
                .findByCustomerNumber(request.getCustomerNumber())
                .ifPresent(existingCustomer -> {

                    // Allow the current customer to keep its own number
                    if (!existingCustomer.getId().equals(id)) {

                        throw new DuplicateCustomerException(
                                "Customer number already exists: "
                                        + request.getCustomerNumber());
                    }
                });

        // Check email
        customerRepository
                .findByEmail(request.getEmail())
                .ifPresent(existingCustomer -> {

                    // Allow the current customer to keep its own email
                    if (!existingCustomer.getId().equals(id)) {

                        throw new DuplicateCustomerException(
                                "Email already exists: "
                                        + request.getEmail());
                    }
                });

        customer.setCustomerNumber(request.getCustomerNumber());
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setAddress(request.getAddress());
        customer.setDateOfBirth(request.getDateOfBirth());

        customer.setUpdatedAt(LocalDateTime.now());

        Customer updatedCustomer = customerRepository.save(customer);

        return convertToResponse(updatedCustomer);
    }

    // Delete customer
    public void deleteCustomer(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer not found with id: " + id));

        customerRepository.delete(customer);
    }

    // Convert Customer entity to CustomerResponse
    private CustomerResponse convertToResponse(Customer customer) {

        CustomerResponse response = new CustomerResponse();

        response.setId(customer.getId());
        response.setCustomerNumber(customer.getCustomerNumber());
        response.setFirstName(customer.getFirstName());
        response.setLastName(customer.getLastName());
        response.setEmail(customer.getEmail());
        response.setPhoneNumber(customer.getPhoneNumber());
        response.setAddress(customer.getAddress());
        response.setDateOfBirth(customer.getDateOfBirth());
        response.setCreatedAt(customer.getCreatedAt());
        response.setUpdatedAt(customer.getUpdatedAt());

        return response;
    }
}