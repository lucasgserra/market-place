package dev.lucasserra.market_place.service

import dev.lucasserra.market_place.domain.product.Product
import dev.lucasserra.market_place.domain.product.dto.ProductRequestDTO
import dev.lucasserra.market_place.exceptions.NotFoundEntity
import dev.lucasserra.market_place.exceptions.StockInsufficientEntity
import dev.lucasserra.market_place.repositories.ProductRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class ProductService(private val repository: ProductRepository) {

    fun createProduct(body: ProductRequestDTO): Product {
        val product: Product = Product.fromDTO(body)
        val saveProduct = repository.save(product)
        return saveProduct
    }

    fun getAllProducts(): List<Product> = repository.findAll()

    fun buyProduct(uuid: String, amount: Int) {
        val optionalProduct: Optional<Product> = repository.findById(uuid)
        if (optionalProduct.isPresent) {
            val product = optionalProduct.get()
            if (!hasAmountProduct(product, amount)) throw StockInsufficientEntity("Product amount is insufficient")
            val updatedProduct = product.copy(
                amount = (product.amount-amount)
            )
            repository.save(updatedProduct)
        } else throw NotFoundEntity("Product not found")
    }

    fun addStockProduct(uuid: String, amount: Int) {
        val optionalProduct: Optional<Product> = repository.findById(uuid)
        if (optionalProduct.isPresent) {
            val product = optionalProduct.get()
            val updatedProduct = product.copy(amount = (product.amount+amount))
            repository.save(updatedProduct)
        } else throw NotFoundEntity("Product not found")
    }

    fun hasAmountProduct(product: Product, amount: Int): Boolean {
        return product.amount >= amount
    }
}