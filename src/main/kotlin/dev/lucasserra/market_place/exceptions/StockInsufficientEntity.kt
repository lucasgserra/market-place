package dev.lucasserra.market_place.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
class StockInsufficientEntity(message: String): RuntimeException(message) {
}