package br.com.caju.payments.domain

sealed class TransactionException(message: String) : RuntimeException(message)

class InsufficientBalanceException : TransactionException("Insufficient balance")

class ProcessingException : TransactionException("Processing error")