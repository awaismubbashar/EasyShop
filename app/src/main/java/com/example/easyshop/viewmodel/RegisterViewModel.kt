package com.example.easyshop.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easyshop.model.UserData
import com.example.easyshop.utils.UserDataStore
import com.example.registrationcompose.utils.DataStoreUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userDataStore: UserDataStore,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

//    private val firestore = Firebase.firestore
    val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
//    private val _user = MutableStateFlow(UserData("", "", ""))
//    val user: StateFlow<UserData> = _user

    private val _user = MutableStateFlow<UserData?>(null)
    val user: StateFlow<UserData?> = _user

    init {
        getUser()
    }

    fun registerUser(
        name: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = task.result?.user?.uid
                    // Save to DataStore
                    viewModelScope.launch {
                        DataStoreUtils.saveData(userDataStore.dataStore, "name", name)
                        DataStoreUtils.saveData(userDataStore.dataStore, "email", email)
                        DataStoreUtils.saveData(userDataStore.dataStore, "uid", uid)
                    }
                    onSuccess()

                    val userModel = UserData(uid!!, name, email)
                    firestore.collection("users").document(uid).set(userModel)
                        .addOnCompleteListener {
                            if(it.isSuccessful) {
                                onSuccess()
                            } else {
                                onError(task.exception?.message ?: "Registration failed")
                            }
                        }
                } else {
                    onError(task.exception?.message ?: "Registration failed")
                }
            }
    }


    fun loginUser(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onError(task.exception?.message ?: "Login failed")
                }
            }
    }

    private fun getUser() {
        viewModelScope.launch {
            val name = DataStoreUtils.getData(userDataStore.dataStore, "name", "").first()
            val email = DataStoreUtils.getData(userDataStore.dataStore, "email", "").first()
            val uid = DataStoreUtils.getData(userDataStore.dataStore, "uid", "").first()
            _user.value = UserData(uid, name, email)
        }
    }

}