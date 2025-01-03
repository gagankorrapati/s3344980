package uk.ac.tees.mad.univid

import android.util.Log
import retrofit2.Call
import retrofit2.Response
import uk.ac.tees.mad.univid.data.remote.PetItems
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val API : PetItems
) {

    suspend fun getPetItems(name : String) = API.getPetItems("aYVdlfjE7dIQHGjNqcF0Xg==4K6r1hZOE6btBrri", name)

}