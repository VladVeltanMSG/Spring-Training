package ro.msg.learning.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.domain.Customer;
import ro.msg.learning.shop.dto.CustomerDto;
import ro.msg.learning.shop.exception.ResourceNotFoundException;
import ro.msg.learning.shop.mapper.CustomerMapper;
import ro.msg.learning.shop.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    public static final String CUSTOMER_WITH_ID = "Customer with ID'";
    public static final String NOT_FOUND = "' not found.";
    public static final String CUSTOMER_NOT_FOUND = "Customer not found";
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerMapper customerMapper;

    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customerMapper.toDtoList(customers);
    }

    public CustomerDto getCustomerById(UUID id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) {
            throw new ResourceNotFoundException(CUSTOMER_NOT_FOUND);
        }
        return customerMapper.toDto(customer);
    }

    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = customerMapper.toEntity(customerDto);
        Customer createdCustomer = customerRepository.save(customer);
        return customerMapper.toDto(createdCustomer);
    }

    public CustomerDto updateCustomer(UUID id, CustomerDto customerDto) {
        Customer existingCustomer = customerRepository.findById(id).orElse(null);

        if (existingCustomer == null) {
            throw new ResourceNotFoundException(CUSTOMER_WITH_ID + id + NOT_FOUND);
        }

        customerMapper.updateCustomerFromDto(customerDto, existingCustomer);
        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return customerMapper.toDto(updatedCustomer);
    }

    public void deleteCustomer(UUID id) {
        customerRepository.deleteById(id);
    }

    public Optional<Customer> findById(UUID idCustomer) {
        return customerRepository.findById(idCustomer);
    }
}
