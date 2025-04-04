package dev.lucasserra.market_place.service

import dev.lucasserra.market_place.domain.product.Product
import dev.lucasserra.market_place.domain.product.dto.ProductRequestDTO
import dev.lucasserra.market_place.exceptions.NotFoundEntity
import dev.lucasserra.market_place.exceptions.StockInsufficientEntity
import dev.lucasserra.market_place.repositories.ProductRepository
import org.springframework.data.jpa.domain.AbstractPersistable_.id
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

    fun getProduct(id: Integer): Optional<Product> {
        return repository.findById(id)
    }

    fun buyProduct(id: Integer, amount: Int) {
        val optionalProduct: Optional<Product> = repository.findById(id)
        if (optionalProduct.isPresent) {
            val product = optionalProduct.get()
            if (!hasAmountProduct(product, amount)) throw StockInsufficientEntity("Product amount is insufficient")
            val updatedProduct = product.copy(
                amount = (product.amount-amount)
            )
            repository.save(updatedProduct)
        } else throw NotFoundEntity("Product not found")
    }

    fun addStockProduct(id: Integer, amount: Int) {
        val optionalProduct: Optional<Product> = repository.findById(id)
        if (optionalProduct.isPresent) {
            val product = optionalProduct.get()
            val updatedProduct = product.copy(amount = (product.amount+amount))
            repository.save(updatedProduct)
        } else throw NotFoundEntity("Product not found")
    }

    fun hasAmountProduct(product: Product, amount: Int): Boolean {
        return product.amount >= amount
    }

    fun deleteProduct(id: Integer) {
        val product = repository.findById(id)
        if (product.isPresent) {
            repository.delete(product.get())
        }
        else throw NotFoundEntity("Product not found")
    }
}