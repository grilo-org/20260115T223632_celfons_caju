package br.com.caju.payments.repository.entity

import java.time.LocalDateTime

abstract class BaseEntity {
    protected val dateCreated = LocalDateTime.now()
}