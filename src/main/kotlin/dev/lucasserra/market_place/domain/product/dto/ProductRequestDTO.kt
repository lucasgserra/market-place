package dev.lucasserra.market_place.domain.product.dto

data class ProductRequestDTO(
    val name: String,
    val price: Double,
    val description: String,
    val amount: Int,
    val seller: String,
    val img: String
)
