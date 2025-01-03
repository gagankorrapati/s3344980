package uk.ac.tees.mad.univid.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PetDao {

    @Query("SELECT * FROM petitems")
    fun getAll(): List<PetItemDB>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPet(pet: PetItemDB)

    @Delete
    fun delete(pet: PetItemDB)

}