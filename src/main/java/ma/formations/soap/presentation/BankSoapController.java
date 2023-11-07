package ma.formations.soap.presentation;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import lombok.AllArgsConstructor;
import ma.formations.soap.common.CommonTools;
import ma.formations.soap.dtos.bankaccount.AddBankAccountRequest;
import ma.formations.soap.dtos.bankaccount.AddBankAccountResponse;
import ma.formations.soap.dtos.bankaccount.BankAccountDto;
import ma.formations.soap.dtos.customer.AddCustomerRequest;
import ma.formations.soap.dtos.customer.AddCustomerResponse;
import ma.formations.soap.dtos.customer.CustomerDto;
import ma.formations.soap.dtos.transaction.AddWirerTransferRequest;
import ma.formations.soap.dtos.transaction.AddWirerTransferResponse;
import ma.formations.soap.dtos.transaction.TransactionDto;
import ma.formations.soap.service.IBankAccountService;
import ma.formations.soap.service.ICustomerService;
import ma.formations.soap.service.ITransactionService;
import ma.formations.soap.service.exception.BusinessException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@WebService(serviceName = "BankWS")
@AllArgsConstructor
public class BankSoapController {

    private final IBankAccountService bankAccountService;
    private ITransactionService transactionService;
    private CommonTools commonTools;
    private final ICustomerService customerService;

    @WebMethod
    public  List<CustomerDto> customers() {
        return customerService.getAllCustomers();
    }

    @WebMethod
    public CustomerDto customerByIdentity(@WebParam(name = "identity") String identity) {
        return customerService.getCustomByIdentity(identity);
    }

    @WebMethod
    public AddCustomerResponse createCustomer(@WebParam(name = "dto") AddCustomerRequest dto) {
        return customerService.createCustomer(dto);
    }

    @WebMethod
   public  List<BankAccountDto> bankAccounts() {
        return bankAccountService.getAllBankAccounts();
    }

    @WebMethod
    public BankAccountDto bankAccountByRib(@WebParam(name="rib") String rib) {
        return bankAccountService.getBankAccountByRib(rib);
    }


    @WebMethod
    public AddBankAccountResponse createBankAccount(@WebParam(name="bankAccountRequest") AddBankAccountRequest dto) {
        return bankAccountService.saveBankAccount(dto);
    }

    @WebMethod
    public AddWirerTransferResponse createWirerTransfer(@WebParam(name = "wirerTransferRequest") AddWirerTransferRequest dto) {
        return transactionService.wiredTransfer(dto);
    }

    @WebMethod
    public List<TransactionDto> getTransactions(@WebParam(name = "rib") String rib, @WebParam(name = "dateFrom") String dateFrom, @WebParam(name = "dateTo") String dateTo) {
        Date from = null;
        Date to = null;
        try {
            from = commonTools.stringToDate(dateFrom);
        } catch (Exception e) {
            throw new BusinessException(String.format("the date %s does not respect the format %s ", dateFrom, commonTools.getDateFormat()));
        }

        try {
            to = commonTools.stringToDate(dateTo);
        } catch (Exception e) {
            throw new BusinessException(String.format("the date %s does not respect the format %s ", dateTo, commonTools.getDateFormat()));
        }
        return transactionService.getTransactions(rib, from, to);
    }

}
