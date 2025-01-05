package uk.ac.tees.mad.univid.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PetDao {

    @Query("SELECT * FROM pet_items")
    fun getAll(): Flow<List<PetItemDB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPet(pet: PetItemDB)

    @Delete
    suspend fun delete(pet: PetItemDB)

}