package dev.taljaard.training.trnmicroservbrewery.services;

import java.util.UUID;

import dev.taljaard.training.trnmicroservbrewery.web.model.CustomerDto;

public interface CustomerService {

    CustomerDto getCustomerById(UUID id);

    CustomerDto createCustomer(CustomerDto customerDto);

    void updateCustomer(UUID customerId, CustomerDto customerDto);

    void deleteCustomerById(UUID customerId);

}
