package uk.ac.tees.mad.univid

import android.util.Log
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response
import uk.ac.tees.mad.univid.data.local.PetDao
import uk.ac.tees.mad.univid.data.local.PetItemDB
import uk.ac.tees.mad.univid.data.remote.PetItems
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val API : PetItems,
    private val petDao : PetDao
) {

    suspend fun getPetItems(name : String) = API.getPetItems("aYVdlfjE7dIQHGjNqcF0Xg==4K6r1hZOE6btBrri", name)

    suspend fun insertToDatabase(petItemsDb: PetItemDB){
        Log.d("Repository", "insertToDatabase: ")
        petDao.insertPet(petItemsDb)
    }

    suspend fun deleteFromDatabase(petItemsDb: PetItemDB){
        petDao.delete(petItemsDb)
    }

    fun getAllFromDatabase(): Flow<List<PetItemDB>> {
        return petDao.getAll()
    }
}