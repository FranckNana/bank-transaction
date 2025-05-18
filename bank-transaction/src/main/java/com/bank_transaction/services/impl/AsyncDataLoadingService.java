package com.bank_transaction.services.impl;

import com.bank_transaction.utils.TransactionsUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncDataLoadingService {

    private final TransactionsUtils transactionsUtils;

    public AsyncDataLoadingService(TransactionsUtils transactionsUtils) {
        this.transactionsUtils = transactionsUtils;
    }

    @Async
    public void asyncLoadDataFromJsonFiles(){
        transactionsUtils.loadReferentielData();
        transactionsUtils.loadTransactionData();
    }
}
