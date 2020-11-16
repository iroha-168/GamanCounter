package com.iroha168.gamancounter.view.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iroha168.gamancounter.repository.UserInfoRepository
import com.jakewharton.rxrelay3.PublishRelay
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.Exception

class UserInfoViewModel : ViewModel() {
    private val firestoreSaveUserSuccess:PublishRelay<FirestoreSuccess> = PublishRelay.create()

    data class FirestoreSuccess(
        val data: String
    )
    fun saveUserNameAndId(userName: String, userId: String, repository: UserInfoRepository = UserInfoRepository()) {
        viewModelScope.launch {
            try {
                val task = async { repository.saveUser(userName, userId) }.await()
                if (task.isSuccessful) {
                    val result = FirestoreSuccess("")
                    firestoreSaveUserSuccess.accept(result)
                }
            } catch (e: Exception) {
                Log.d("ERROR", e.toString())
            }
        }
    }
}