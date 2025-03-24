package dev.lucasserra.market_place.repositories

import dev.lucasserra.market_place.domain.product.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository: JpaRepository<Product, String> {
}