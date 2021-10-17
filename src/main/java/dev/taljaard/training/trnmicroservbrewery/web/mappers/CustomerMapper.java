package dev.taljaard.training.trnmicroservbrewery.web.mappers;

import org.mapstruct.Mapper;

import dev.taljaard.training.trnmicroservbrewery.domain.Customer;
import dev.taljaard.training.trnmicroservbrewery.web.model.CustomerDto;

@Mapper(uses = { DateMapper.class })
public interface CustomerMapper {

    CustomerDto customerToCustomerDto(Customer customer);

    Customer customerDtoToCustomer(CustomerDto customerDto);
}
