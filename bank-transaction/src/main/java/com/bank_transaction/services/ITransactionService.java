package com.bank_transaction.services;

import com.bank_transaction.models.dto.TransactionDto;

import java.util.List;

public interface ITransactionService {

    public List<TransactionDto> getTransactions(String id);
    public List<TransactionDto> getInvalidTransactions();
}
