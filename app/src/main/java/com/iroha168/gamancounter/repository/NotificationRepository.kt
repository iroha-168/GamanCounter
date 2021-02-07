package com.iroha168.gamancounter.repository

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.iroha168.gamancounter.view.model.TestNotificationDataClass
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class NotificationRepository {
    // uidとトークンを登録
    suspend fun save(uid: String?, token: String?): Task<Void> {
        return suspendCoroutine { cont ->
            val db = FirebaseFirestore.getInstance()
            val testNotification = TestNotificationDataClass(uid, token)
            val task = db.collection("testNotification")
                .document()
                .set(testNotification)
            Log.d("TOKEN", "about to enter addOnCompleteListener")
            task.addOnCompleteListener {
                Log.d("TOKEN", "in the addOnCompleteListener")
                cont.resume(it)
            }
            Log.d("TOKEN", "finish addOnCompleteListener")
        }
    }
}