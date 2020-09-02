package io.ambar.featuretoggles.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.ambar.featuretoggles.dto.request.BindFeatureToCustomerRequest;
import io.ambar.featuretoggles.repository.CustomerRepository;
import io.ambar.featuretoggles.repository.FeatureTogglesRepository;
import io.ambar.featuretoggles.repository.entity.Customer;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController {
    private final CustomerRepository customerRepository;
    private final FeatureTogglesRepository featureToggleRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository,
            FeatureTogglesRepository featureTogglesRepository) {
        this.customerRepository = customerRepository;
        this.featureToggleRepository = featureTogglesRepository;
    }

    @GetMapping(path = "customer")
    public List<Customer> getCustomers() {
        return StreamSupport.stream(customerRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @PatchMapping(path = "customer/feature")
    public void bindFeatureToCustomer(@RequestBody BindFeatureToCustomerRequest request) {
        featureToggleRepository.findById(request.getFeatureId()).ifPresent(feature -> customerRepository
                .findById(request.getCustomerId()).ifPresent(customer -> {
                    if (request.isActive()) {
                        customer.getFeatureToggles().add(feature);
                    } else {
                        customer.getFeatureToggles().remove(feature);
                    }

                    customerRepository.save(customer);
                }));
    }
}