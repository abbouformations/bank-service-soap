package ma.formations.soap.service;

import ma.formations.soap.dtos.transaction.AddWirerTransferRequest;
import ma.formations.soap.dtos.transaction.AddWirerTransferResponse;
import ma.formations.soap.dtos.transaction.TransactionDto;

import java.util.Date;
import java.util.List;

public interface ITransactionService {
    AddWirerTransferResponse wiredTransfer(AddWirerTransferRequest dto);

    List<TransactionDto> getTransactions(String rib, Date dateFrom, Date dateTo);
}
