package com.bank_transaction.controllers;

import com.bank_transaction.exceptions.ControllerExceptionHandler;
import com.bank_transaction.models.dto.TransactionDto;
import com.bank_transaction.services.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionsController extends ControllerExceptionHandler {

    @Autowired
    private ITransactionService transactionService;

    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionDto>> findTransactChainById(@RequestParam String id){
        List<TransactionDto> transactions = this.transactionService.getTransactions(id);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/invalidtransact")
    public ResponseEntity<List<TransactionDto>> findInvaliTransactById(){
        List<TransactionDto> transactions = this.transactionService.getInvalidTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}
