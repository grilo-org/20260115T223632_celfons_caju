package br.com.caju.payments

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@OpenAPIDefinition(info = Info(title = "Caju Payments", version = "0.0.1"))
class PaymentsApplication

fun main(args: Array<String>) {
	runApplication<PaymentsApplication>(*args)
}
