package uk.ac.tees.mad.univid.data.remote

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import uk.ac.tees.mad.univid.model.PetItem

interface PetItems {
    @GET("v1/cats")
    suspend fun getPetItems(@Header ("x-api-key") key: String,@Query ("name") name: String): Response<List<PetItem>>
}