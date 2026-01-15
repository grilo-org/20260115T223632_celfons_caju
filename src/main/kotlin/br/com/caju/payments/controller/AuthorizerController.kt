package br.com.caju.payments.controller

import br.com.caju.payments.controller.request.TransactionRequest
import br.com.caju.payments.service.AuthorizerService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestBody

@RestController
@RequestMapping("/v1/authorizer")
@Tag(name = "Authorizer Controller", description = "Authorizer to receive transactions")
class AuthorizerController(
    @Autowired private val service: AuthorizerService
) {

    @PostMapping(value  = ["/l1"], produces = ["application/json"])
    @Operation(summary = "Simple Authorizer", description = "API to authorize transactions")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "Authorize transaction", content = [Content(mediaType = "application/json",
            examples = [
                ExampleObject(name = "Response to transaction approved", summary = "Successful response", value = """{ "code": "00" }"""),
                ExampleObject(name = "Response to transaction rejected", summary = "Insufficient balance", value = """{ "code": "51" }"""),
                ExampleObject(name = "Response to transaction failed", summary = "Any exception occurred", value = """{ "code": "07" }""")
            ])])])
    fun simpleAuthorizer(@RequestBody @Valid request : TransactionRequest): ResponseEntity<Map<String, String>> {
        service.simpleAuthorizer(request.toDomain())
        return ResponseEntity.ok(mapOf("code" to "00"))
    }

    @PostMapping(value  = ["/l2"], produces = ["application/json"])
    @Operation(summary = "Authorizer with Fallback", description = "API to authorize transactions")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "Authorize transaction", content = [Content(mediaType = "application/json",
        examples = [
            ExampleObject(name = "Response to transaction approved", summary = "Successful response", value = """{ "code": "00" }"""),
            ExampleObject(name = "Response to transaction rejected", summary = "Insufficient balance", value = """{ "code": "51" }"""),
            ExampleObject(name = "Response to transaction failed", summary = "Any exception occurred", value = """{ "code": "07" }""")
        ])])])
    fun fallbackAuthorizer(@RequestBody @Valid request : TransactionRequest): ResponseEntity<Map<String, String>> {
        service.fallbackAuthorizer(request.toDomain())
        return ResponseEntity.ok(mapOf("code" to "00"))
    }

    @PostMapping(value  = ["/l3"], produces = ["application/json"])
    @Operation(summary = "Merchant Dependent Authorizer", description = "API to authorize transactions")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "Authorize transaction", content = [Content(mediaType = "application/json",
        examples = [
            ExampleObject(name = "Response to transaction approved", summary = "Successful response", value = """{ "code": "00" }"""),
            ExampleObject(name = "Response to transaction rejected", summary = "Insufficient balance", value = """{ "code": "51" }"""),
            ExampleObject(name = "Response to transaction failed", summary = "Any exception occurred", value = """{ "code": "07" }""")
        ])])])
    fun merchantDependentAuthorizer(@RequestBody @Valid request : TransactionRequest): ResponseEntity<Map<String, String>> {
        service.merchantDependentAuthorizer(request.toDomain())
        return ResponseEntity.ok(mapOf("code" to "00"))
    }

}