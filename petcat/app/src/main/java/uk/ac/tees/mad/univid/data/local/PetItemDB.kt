package uk.ac.tees.mad.univid.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pet_items")
data class PetItemDB (
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val children_friendly: Int,
    val family_friendly: Int,
    val general_health: Int,
    val grooming: Int,
    val image_link: String,
    val intelligence: Int,
    val length: String,
    val max_life_expectancy: Double,
    val max_weight: Double,
    val meowing: Int,
    val min_life_expectancy: Double,
    val min_weight: Double,
    val name: String,
    val origin: String,
    val other_pets_friendly: Int,
    val playfulness: Int,
    val shedding: Int,
    val stranger_friendly: Int
)