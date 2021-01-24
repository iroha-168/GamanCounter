package com.iroha168.gamancounter.repository

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.iroha168.gamancounter.view.model.SaveUserInfo
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserInfoRepository {
    // ユーザー情報を保存
    suspend fun saveUser(
        uid: String?,
        userName: String
    ): Task<Void> {
        return suspendCoroutine { cont ->
            val db = FirebaseFirestore.getInstance()
            val saveUserInfo = SaveUserInfo(uid, userName)
            val task = db.collection("userInfo")
                .document()
                .set(saveUserInfo)
            task.addOnCompleteListener {
                cont.resume(it)
            }
                .addOnFailureListener { e -> Log.w("TAG", "Error setting document", e) }
        }
    }

    // 引数のuidと一致するユーザー情報を取得
    suspend fun getUser(uid: String?): List<SaveUserInfo> {
        return suspendCoroutine { cont ->
            val db = FirebaseFirestore.getInstance()
            val task = db.collection("userInfo")
                .whereEqualTo("uid", uid)
                .get()
            task.addOnCompleteListener {
                val resultList = task.result!!.toObjects(SaveUserInfo::class.java)
                cont.resume(resultList)
            }
        }
    }
}