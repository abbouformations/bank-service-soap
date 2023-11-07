package ma.formations.soap.service;

import ma.formations.soap.dtos.customer.AddCustomerRequest;
import ma.formations.soap.dtos.customer.AddCustomerResponse;
import ma.formations.soap.dtos.customer.CustomerDto;

import java.util.List;

public interface ICustomerService {

    List<CustomerDto> getAllCustomers();

    AddCustomerResponse createCustomer(AddCustomerRequest addCustomerRequest);

    CustomerDto getCustomByIdentity(String identity);


}
