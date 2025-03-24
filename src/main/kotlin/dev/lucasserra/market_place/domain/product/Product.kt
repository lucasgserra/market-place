package dev.lucasserra.market_place.domain.product

import dev.lucasserra.market_place.domain.product.dto.ProductRequestDTO
import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
data class Product(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null,
    @JoinColumn(nullable = false)
    var name: String,
    @JoinColumn(nullable = false)
    var description: String,
    @JoinColumn(nullable = false)
    var price: Double,
    @JoinColumn(nullable = false)
    var amount: Int,
    @JoinColumn(nullable = false)
    var seller: String,
    @JoinColumn(nullable = false)
    var img: String

) {
    companion object {
        fun fromDTO(data: ProductRequestDTO) = Product(
            name = data.name,
            description = data.description,
            price = data.price,
            amount = data.amount,
            seller = data.seller,
            img = data.img
        )
    }
}
