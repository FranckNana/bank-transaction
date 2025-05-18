package com.bank_transaction.exceptions.exceptionType;

import com.bank_transaction.exceptions.GlobalException;

public class BadRequestException extends GlobalException {
	public BadRequestException(String message) {
		super(message);
	}
}
