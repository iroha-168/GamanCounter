package com.iroha168.gamancounter.view.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iroha168.gamancounter.repository.UserInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserInfoViewModel : ViewModel() {
    private val repository = UserInfoRepository()

    // ユーザー名とユーザーIDを保存
    fun saveUserNameAndId(userName: String, userId: String) {
        viewModelScope.launch {
            try {
                val task = withContext(Dispatchers.Default) {
                    repository.saveUser(
                        userName,
                        userId
                    )
                }
            } catch (e: Exception) {
                Log.d("ERROR", e.toString())
            }
        }
    }

    // ユーザー名とユーザーIDを取得
    private val _postsData = MutableLiveData<List<SaveUserInfo>>()
    val postsData: LiveData<List<SaveUserInfo>> = _postsData

    fun loadUserNameAndId() = viewModelScope.launch {
        try {
            val posts = withContext(Dispatchers.Default) { repository.getUser() }
        } catch (e: Exception) {
            Log.d("ERROR", e.toString())
        }
    }
}