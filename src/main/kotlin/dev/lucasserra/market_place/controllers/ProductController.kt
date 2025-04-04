package dev.lucasserra.market_place.controllers

import dev.lucasserra.market_place.domain.product.Product
import dev.lucasserra.market_place.domain.product.dto.ProductStockDTO
import dev.lucasserra.market_place.domain.product.dto.ProductRequestDTO
import dev.lucasserra.market_place.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product")
class ProductController(private val service: ProductService) {

    @PostMapping
    fun postProduct(@RequestBody body: ProductRequestDTO): ResponseEntity<Product> {
        val product = service.createProduct(body)
        return ResponseEntity.status(HttpStatus.CREATED).body(product)
    }

    @PostMapping("/buy")
    fun buyProduct(@RequestBody productStock: ProductStockDTO): ResponseEntity<Void> {
        service.buyProduct(productStock.uuid, productStock.amount)
        return ResponseEntity.status(HttpStatus.ACCEPTED).build()
    }

    @PostMapping("/addStock")
    fun addStockProduct(@RequestBody productStock: ProductStockDTO): ResponseEntity<Void> {
        service.addStockProduct(productStock.uuid, productStock.amount)
        return ResponseEntity.status(HttpStatus.ACCEPTED).build()
    }

    @GetMapping("/{uuid}")
    fun findProduct(@PathVariable uuid: String): ResponseEntity<Product> {
        val product = service.getProduct(uuid);
        return if (product.isPresent) {
            ResponseEntity.status(HttpStatus.ACCEPTED).body(product.get());
        } else ResponseEntity.status(HttpStatus.NOT_FOUND).build()
    }

    @DeleteMapping("/{uuid}")
    fun deleteProduct(@PathVariable uuid: String): ResponseEntity<Void> {
        service.deleteProduct(uuid)
        return ResponseEntity.status(HttpStatus.OK).build()
    }

    @GetMapping
    fun findAllProduct(): ResponseEntity<List<Product>> {
        return ResponseEntity.status(HttpStatus.FOUND).body(service.getAllProducts())
    }
}