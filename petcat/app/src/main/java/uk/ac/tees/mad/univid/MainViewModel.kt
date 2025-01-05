package uk.ac.tees.mad.univid

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import uk.ac.tees.mad.univid.data.local.PetItemDB
import uk.ac.tees.mad.univid.data.remote.PetItems
import uk.ac.tees.mad.univid.model.PetItem
import uk.ac.tees.mad.univid.model.User
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val auth : FirebaseAuth,
    private val firestore : FirebaseFirestore,
    private val repository: AppRepository
) : ViewModel() {


    var petItems by mutableStateOf<List<PetItem>>(emptyList())
        private set

    var userData by mutableStateOf<User?>(null)
        private set

    val petItemsFlow = MutableStateFlow<List<PetItemDB>>(emptyList())

    val isLoading = mutableStateOf(false)
    val isSignedIn = mutableStateOf(false)

    init {
        if(auth.currentUser != null){
            isSignedIn.value = true
            getUserData(auth.currentUser!!.uid)
        }
        fetchApi()
    }


    fun signUp(context : Context, name : String, email : String, password : String){
        isLoading.value = true
        auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
            firestore.collection("users").document(it.user!!.uid).set(
                hashMapOf(
                    "image" to "",
                    "name" to name,
                    "email" to email,
                    "password" to password
                )
            ).addOnSuccessListener {
                Log.d("user", "user updated successfully")
                isLoading.value = false
                Toast.makeText(context, "Sign Up successful", Toast.LENGTH_SHORT).show()
                isSignedIn.value = true
                getUserData(auth.currentUser!!.uid)
            }.addOnFailureListener {
                Log.d("user", "user updated failed")
                isLoading.value = false
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Log.d("user", "user created failed")
            isLoading.value = false
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    fun logIn(context: Context, email: String, password: String){
        isLoading.value = true
        auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
            isLoading.value = false
            Log.d("user", "user logged in successfully")
            Toast.makeText(context, "Log In successful", Toast.LENGTH_SHORT).show()
            isSignedIn.value = true
            getUserData(it.user!!.uid)
        }.addOnFailureListener {
            isLoading.value = false
            Log.d("user", "user logged in failed")
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    fun getUserData(uid: String){
        firestore.collection("users").document(uid).get().addOnSuccessListener {
            val user = it.data
            userData = User(
                image = user?.get("image").toString(),
                name = user?.get("name").toString(),
                email = user?.get("email").toString(),
                password = user?.get("password").toString()
            )
            Log.d("user", "user data fetched successfully")
        }.addOnFailureListener {
            Log.d("user", "user data fetched failed")
        }
    }

    fun fetchApi() {
        val alpha = ('a'..'z')
        val randomAlpha = alpha.random()
        viewModelScope.launch {
            val response = repository.getPetItems("h")
            if (response.isSuccessful) {
                petItems = response.body() ?: emptyList()  // Provide a default value if null
                Log.d("api", petItems.toString())
            } else {
                Log.d("api", "Failed to fetch data, Status code: ${response.code()}")
            }
        }
    }

    fun saveToDB(petItem: PetItem) {
        val item = petItem
        viewModelScope.launch {
            repository.insertToDatabase(
                PetItemDB(
                    children_friendly = item.children_friendly,
                    family_friendly = item.family_friendly,
                    general_health = item.general_health,
                    grooming = item.grooming,
                    image_link = item.image_link,
                    intelligence = item.intelligence,
                    length = item.length,
                    max_life_expectancy = item.max_life_expectancy,
                    max_weight = item.max_weight,
                    meowing = item.meowing,
                    min_life_expectancy = item.min_life_expectancy,
                    min_weight = item.min_weight,
                    name = item.name,
                    origin = item.origin,
                    other_pets_friendly = item.other_pets_friendly,
                    playfulness = item.playfulness,
                    shedding = item.shedding,
                    stranger_friendly = item.stranger_friendly
                )
            )
            Log.d("Database", "Inserted item: ${petItem.name}")
        }
    }

    fun getAllFromDb(){
        viewModelScope.launch {
            repository.getAllFromDatabase().collect{
                Log.d("Database", "Collected ${it.size} items from DB")
                petItemsFlow.value = it
            }
        }
    }

    fun deletePet(pet : PetItemDB){
        viewModelScope.launch {
            repository.deleteFromDatabase(pet)
        }
    }

}