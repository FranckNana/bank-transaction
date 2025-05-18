package com.bank_transaction.exceptions.exceptionType;

import com.bank_transaction.exceptions.GlobalException;

public class NotFoundException extends GlobalException {
	public NotFoundException(String message) {
		super(message);
	}
}
