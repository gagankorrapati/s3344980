package uk.ac.tees.mad.univid.model

data class PetItem(
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