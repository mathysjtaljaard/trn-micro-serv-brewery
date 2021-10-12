package dev.taljaard.training.trnmicroservbrewery.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.taljaard.training.trnmicroservbrewery.web.model.CustomerDto;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Override
    public CustomerDto getCustomerById(UUID id) {
        return CustomerDto.builder().id(id).name("Hungry Hippo").build();
    }

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        return CustomerDto.builder().id(UUID.randomUUID()).name(customerDto.getName()).build();
    }

    @Override
    public void updateCustomer(UUID customerId, CustomerDto customerDto) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteCustomerById(UUID customerId) {
        // TODO Auto-generated method stub

    }

}
