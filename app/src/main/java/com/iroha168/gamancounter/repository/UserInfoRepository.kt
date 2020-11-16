package com.iroha168.gamancounter.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.iroha168.gamancounter.view.model.SaveUserInfo
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserInfoRepository {
    suspend fun saveUser(userName: String?, userId: String) : Task<Void> {
        return suspendCoroutine { cont ->
            val db = FirebaseFirestore.getInstance()
            val saveUserInfo = SaveUserInfo(userName, userId)
            val task = db.collection("userInfo")
                .document()
                .set(saveUserInfo)
            task.addOnCompleteListener { task ->
                cont.resume(task)
            }
        }
    }
}