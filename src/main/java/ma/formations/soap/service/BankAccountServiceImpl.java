package ma.formations.soap.service;


import ma.formations.soap.dao.BankAccountRepository;
import ma.formations.soap.dao.CustomerRepository;
import ma.formations.soap.dtos.bankaccount.AddBankAccountRequest;
import ma.formations.soap.dtos.bankaccount.AddBankAccountResponse;
import ma.formations.soap.dtos.bankaccount.BankAccountConverter;
import ma.formations.soap.dtos.bankaccount.BankAccountDto;
import ma.formations.soap.enums.AccountStatus;
import ma.formations.soap.service.exception.BusinessException;
import ma.formations.soap.service.model.BankAccount;
import ma.formations.soap.service.model.Customer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class BankAccountServiceImpl implements IBankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final CustomerRepository customerRepository;
    private final BankAccountConverter bankAccountConverter;


    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, CustomerRepository customerRepository, BankAccountConverter bankAccountConverter) {
        this.bankAccountRepository = bankAccountRepository;
        this.customerRepository = customerRepository;
        this.bankAccountConverter = bankAccountConverter;
    }


    @Override
    public AddBankAccountResponse saveBankAccount(AddBankAccountRequest dto) {
        BankAccount bankAccount = bankAccountConverter.AddBankAccountRequestToBankAccount(dto);
        Customer customerP = customerRepository.findByIdentityRef(bankAccount.getCustomer().getIdentityRef()).orElseThrow(
                () -> new BusinessException(String.format("No customer with the identity: %s exist", dto.getCustomerIdentityRef())));
        bankAccount.setAccountStatus(AccountStatus.OPENED);
        bankAccount.setCustomer(customerP);
        bankAccount.setCreatedAt(new Date());
        AddBankAccountResponse response = bankAccountConverter.bankAccountToAddBankAccountResponse(bankAccountRepository.save(bankAccount));
        response.setMessage(String.format("RIB number [%s] for the customer [%s] has been successfully created", dto.getRib(), dto.getCustomerIdentityRef()));
        return response;
    }

    @Override
    public List<BankAccountDto> getAllBankAccounts() {
        return bankAccountConverter.bankAccountDtos(bankAccountRepository.findAll());
    }

    @Override
    public BankAccountDto getBankAccountByRib(String rib) {
        return bankAccountConverter.bankAccountToBankAccountDTO(bankAccountRepository.findByRib(rib).orElseThrow(
                () -> new BusinessException(String.format("No Bank Account with rib [%s] exist", rib))));
    }
}
