package br.com.caju.payments.controller.exception

import br.com.caju.payments.domain.InsufficientBalanceException
import br.com.caju.payments.domain.ProcessingException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(InsufficientBalanceException::class)
    @ResponseStatus(HttpStatus.OK) // Para enviar uma resposta 200 OK
    @ResponseBody
    fun handleSaldoInsuficienteException(ex: InsufficientBalanceException): Map<String, String> {
        return mapOf("code" to "51")
    }

    @ExceptionHandler(ProcessingException::class)
    @ResponseStatus(HttpStatus.OK) // Para enviar uma resposta 200 OK
    @ResponseBody
    fun handleProblemaProcessamentoException(ex: ProcessingException): Map<String, String> {
        return mapOf("code" to "07")
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.OK) // Para enviar uma resposta 200 OK
    @ResponseBody
    fun handleGenericException(ex: Exception): Map<String, String> {
        return mapOf("code" to "07")
    }
}
