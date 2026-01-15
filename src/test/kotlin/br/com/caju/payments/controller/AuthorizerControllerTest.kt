package br.com.caju.payments.controller

import java.math.BigDecimal
import java.util.*

import br.com.caju.payments.controller.request.TransactionRequest
import br.com.caju.payments.service.AuthorizerService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@ExtendWith(MockitoExtension::class)
class AuthorizerControllerTest {

    @Mock
    private lateinit var authorizerService: AuthorizerService

    @InjectMocks
    private lateinit var authorizerController: AuthorizerController

    @Test
    fun `test simpleAuthorizer`() {
        val request = TransactionRequest(UUID.randomUUID(), "account", BigDecimal.TEN, "CAJU", "5411")
        doNothing().`when`(authorizerService).simpleAuthorizer(request.toDomain())

        val response: ResponseEntity<Map<String, String>> = authorizerController.simpleAuthorizer(request)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals("00", response.body?.get("code"))
        verify(authorizerService, times(1)).simpleAuthorizer(request.toDomain())
    }

    @Test
    fun `test fallbackAuthorizer`() {
        val request = TransactionRequest(UUID.randomUUID(), "account", BigDecimal.TEN, "CAJU", "5411")
        doNothing().`when`(authorizerService).fallbackAuthorizer(request.toDomain())

        val response: ResponseEntity<Map<String, String>> = authorizerController.fallbackAuthorizer(request)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals("00", response.body?.get("code"))
        verify(authorizerService, times(1)).fallbackAuthorizer(request.toDomain())
    }

    @Test
    fun `test merchantDependentAuthorizer`() {
        val request = TransactionRequest(UUID.randomUUID(), "account", BigDecimal.TEN, "CAJU", "5411")
        doNothing().`when`(authorizerService).merchantDependentAuthorizer(request.toDomain())

        val response: ResponseEntity<Map<String, String>> = authorizerController.merchantDependentAuthorizer(request)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals("00", response.body?.get("code"))
        verify(authorizerService, times(1)).merchantDependentAuthorizer(request.toDomain())
    }
}