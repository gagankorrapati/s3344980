package uk.ac.tees.mad.univid.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uk.ac.tees.mad.univid.data.local.PetDao
import uk.ac.tees.mad.univid.data.local.PetDatabase
import uk.ac.tees.mad.univid.data.remote.PetItems
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesAuthentication() : FirebaseAuth = Firebase.auth

    @Provides
    fun providesFirestore() : FirebaseFirestore = Firebase.firestore

    @Provides
    fun providesStorage() : FirebaseStorage = Firebase.storage

    @Provides
    fun providesRetrofit () : Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.api-ninjas.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit
    }

    @Provides
    fun providesApi(retrofit: Retrofit): PetItems {
        return retrofit.create(PetItems::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): PetDatabase {
        return Room.databaseBuilder(
            appContext,
            PetDatabase::class.java,
            "example_database"
        ).build()
    }

    @Provides
    fun provideExampleDao(petDatabase: PetDatabase): PetDao {
        return petDatabase.petDao()
    }
}