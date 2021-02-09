package com.iroha168.gamancounter.view.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iroha168.gamancounter.repository.CheerMailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CheerMailViewModel : ViewModel() {
    // ユーザー名とチアメールを保存
    fun saveCheerMail(uid: String?, userName: String?, cheerMail: String?) = viewModelScope.launch {
        try {
            withContext(Dispatchers.Default) {
                repository.send(
                    uid,
                    userName,
                    cheerMail
                )
            }
        } catch (e: Exception) {
            Log.d("ERROR", e.toString())
        }
    }

    // ユーザー名とチアメールを取得
    private val _postsData = MutableLiveData<List<CheerMailDataClass>>()
    val postsData: LiveData<List<CheerMailDataClass>> = _postsData

    private val repository = CheerMailRepository()

    fun loadCheerMail(uid: String?) = viewModelScope.launch {
        try {
            // TODO: repositoryの関数を呼び出す
            withContext(Dispatchers.Default) {repository.get(uid)}
        } catch (e: Exception) {
            Log.d("ERROR", e.toString())
        }
    }
}