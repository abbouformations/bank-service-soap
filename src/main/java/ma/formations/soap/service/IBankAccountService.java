package ma.formations.soap.service;

import ma.formations.soap.dtos.bankaccount.AddBankAccountRequest;
import ma.formations.soap.dtos.bankaccount.AddBankAccountResponse;
import ma.formations.soap.dtos.bankaccount.BankAccountDto;

import java.util.List;

public interface IBankAccountService {

    AddBankAccountResponse saveBankAccount(AddBankAccountRequest dto);

    List<BankAccountDto> getAllBankAccounts();

    BankAccountDto getBankAccountByRib(String rib);

}
