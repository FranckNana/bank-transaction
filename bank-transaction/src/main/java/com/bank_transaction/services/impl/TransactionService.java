package com.bank_transaction.services.impl;

import com.bank_transaction.exceptions.exceptionType.BadRequestException;
import com.bank_transaction.exceptions.exceptionType.NotFoundException;
import com.bank_transaction.models.dto.TransactionDto;
import com.bank_transaction.models.entities.Referentiel;
import com.bank_transaction.models.entities.Transaction;
import com.bank_transaction.repository.TransactionRepository;
import com.bank_transaction.services.ITransactionService;
import com.bank_transaction.utils.Constants;
import com.bank_transaction.utils.TransactionsUtils;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransactionService implements ITransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private final TransactionsUtils transactionsUtils;
    private final TransactionRepository transactionRepository;
    private final AsyncDataLoadingService asyncService;

    public TransactionService(TransactionsUtils transactionsUtils, TransactionRepository transactionRepository, AsyncDataLoadingService asyncService) {
        this.transactionsUtils = transactionsUtils;
        this.transactionRepository = transactionRepository;
        this.asyncService = asyncService;
    }

    @PostConstruct
    public void loadDataFromJsonFiles(){
        this.asyncService.asyncLoadDataFromJsonFiles();
    }


    @Override
    public List<TransactionDto> getTransactions(String id) {

        if(Objects.isNull(id)) {
            logger.error(Constants.EXCEPTION_PARAMS_REQUIRED);
            throw new BadRequestException(Constants.EXCEPTION_PARAMS_REQUIRED);
        }

        Map<String, Transaction> allTransactions = this.transactionRepository.findAll().stream()
                .collect(Collectors.toMap(Transaction::getPrimaryId, t -> t));

        List<Transaction> listOfChain = new ArrayList<>();

        Transaction transaction = allTransactions.get(id);

        if(Objects.isNull(transaction)) {
            logger.error(Constants.NOT_FOUND_EXCEPTION);
            throw new NotFoundException(Constants.NOT_FOUND_EXCEPTION);
        }

        while (Objects.nonNull(transaction)) {
            listOfChain.add(transaction);
            transaction = allTransactions.get(transaction.getSecondaryId());
        }

        listOfChain.sort(Comparator.comparingInt((Transaction t) -> {
                    Referentiel ref = t.getReference();
                    return ref != null ? ref.getStepRank() : Integer.MAX_VALUE;
                })
                .thenComparingInt(t -> {
                    Referentiel ref = t.getReference();
                    return ref != null ? ref.getEventRank() : Integer.MAX_VALUE;
                }));

        return listOfChain.stream()
                          .map(transactionsUtils::getTransactionDto)
                          .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDto> getInvalidTransactions() {
        List<TransactionDto> transactions = this.transactionRepository.findAll().stream()
                .filter(t -> Objects.isNull(t.getEventDateTime()))
                .map(transactionsUtils::getTransactionDto)
                .collect(Collectors.toList());

        transactions.sort(Comparator.comparingInt((TransactionDto t) -> {
                    return t != null ? t.getStepRank() : Integer.MAX_VALUE;
                })
                .thenComparingInt(t -> {
                    return t != null ? t.getEventRank() : Integer.MAX_VALUE;
                }));

        return transactions;
    }

}
