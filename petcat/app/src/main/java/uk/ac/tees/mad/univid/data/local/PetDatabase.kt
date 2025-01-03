package uk.ac.tees.mad.univid.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PetItemDB::class], version = 1, exportSchema = false)
abstract class PetDatabase : RoomDatabase() {
    abstract fun petDao(): PetDao
}