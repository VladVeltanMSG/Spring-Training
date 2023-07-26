package ro.msg.learning.shop.mapper;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.domain.Customer;
import ro.msg.learning.shop.dto.CustomerDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerMapper {

    public Customer toEntity(CustomerDto customerDto) {
        Customer customer = new Customer();
        updateCustomerFromDto(customerDto, customer);
        return customer;
    }

    public CustomerDto toDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setUserName(customer.getUsername());
        customerDto.setPassword(customer.getPassword());
        customerDto.setEmailAddress(customer.getEmailAddress());
        return customerDto;
    }

    public List<CustomerDto> toDtoList(List<Customer> customers) {
        return customers.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public void updateCustomerFromDto(CustomerDto customerDto, Customer customer) {
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setUsername(customerDto.getUserName());
        customer.setPassword(customerDto.getPassword());
        customer.setEmailAddress(customerDto.getEmailAddress());
    }
}

