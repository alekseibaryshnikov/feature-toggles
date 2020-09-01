package io.ambar.featuretoggles.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping(path = "customer/feature")
    public void bindFeatureToCustomer(@RequestBody BindFeatureToCustomerRequest request) {
        featureToggleRepository.findById(request.getFeatureId()).ifPresent(feature -> customerRepository
                .findById(request.getFeatureId()).ifPresent(customer -> customer.getFeatureToggles().add(feature)));
    }

    @DeleteMapping(path = "customer/feature")
    public void unbindFeatureFromCustomer(@RequestParam Long featureId, @RequestParam Long customerId) {
        featureToggleRepository.findById(featureId).ifPresent(feature -> customerRepository.findById(customerId)
                .ifPresent(customer -> customer.getFeatureToggles().remove(feature)));
    }
}