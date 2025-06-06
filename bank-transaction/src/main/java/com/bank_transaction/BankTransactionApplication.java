package com.bank_transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BankTransactionApplication {
	public static void main(String[] args) {
		SpringApplication.run(BankTransactionApplication.class, args);
	}
}
