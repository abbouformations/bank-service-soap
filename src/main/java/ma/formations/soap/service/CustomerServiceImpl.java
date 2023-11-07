package ma.formations.soap.service;

import lombok.AllArgsConstructor;
import ma.formations.soap.dao.CustomerRepository;
import ma.formations.soap.dtos.customer.AddCustomerRequest;
import ma.formations.soap.dtos.customer.AddCustomerResponse;
import ma.formations.soap.dtos.customer.CustomerConverter;
import ma.formations.soap.dtos.customer.CustomerDto;
import ma.formations.soap.service.exception.BusinessException;
import ma.formations.soap.service.model.Customer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerConverter customerConverter;

    @Override
    public List<CustomerDto> getAllCustomers() {
        return customerConverter.customerDtos(customerRepository.findAll());
    }


    @Override
    public AddCustomerResponse createCustomer(AddCustomerRequest addCustomerRequest) {
        Customer bo = customerConverter.addCustomerRequestToCustomer(addCustomerRequest);
        String identityRef = bo.getIdentityRef();
        String username = bo.getUsername();

        customerRepository.findByIdentityRef(identityRef).ifPresent(a ->
                {
                    throw new BusinessException(String.format("Customer with the same identity [%s] exist", identityRef));
                }
        );

        customerRepository.findByUsername(username).ifPresent(a ->
                {
                    throw new BusinessException(String.format("The username [%s] is already used", username));
                }
        );

        AddCustomerResponse response = customerConverter.customerToAddCustomerResponse(customerRepository.save(bo));
        response.setMessage(String.format("Customer : [identity= %s,First Name= %s, Last Name= %s, username= %s] was created with success",
                response.getIdentityRef(), response.getFirstname(), response.getLastname(), response.getUsername()));
        return response;
    }

    @Override
    public CustomerDto getCustomByIdentity(String identity) {
        return customerConverter.customerToCustomerDTO(customerRepository.findByIdentityRef(identity).orElseThrow(
                () -> new BusinessException(String.format("No Customer with identity [%s] exist ", identity))));
    }
}
