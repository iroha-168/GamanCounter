package com.iroha168.gamancounter.view.model

import android.util.Log
import androidx.lifecycle.*
import com.iroha168.gamancounter.repository.UserInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserInfoViewModel : ViewModel() {
    private val _getData = MutableLiveData<List<SaveUserInfo>>()
    val getData: LiveData<List<SaveUserInfo>>
    get() = _getData

    private val repository = UserInfoRepository()

    // ユーザー名とユーザーIDを保存
    fun saveUserNameAndId(uid: String?, userName: String) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.Default) {
                    repository.saveUser(
                        uid,
                        userName
                    )
                }
            } catch (e: Exception) {
                Log.d("ERROR", e.toString())
            }
        }
    }

    // uidからユーザー名を取得
    fun loadUserNameAndId(uid: String?) = viewModelScope.launch {
        try {
            val userData = withContext(Dispatchers.Default) { repository.getUser(uid) }
            _getData.value = userData
        } catch (e: Exception) {
            Log.d("ERROR", e.toString())
        }
    }
}