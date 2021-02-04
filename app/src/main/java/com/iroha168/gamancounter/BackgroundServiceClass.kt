package com.iroha168.gamancounter

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.iroha168.gamancounter.repository.TestNotificationRepository
import kotlinx.coroutines.runBlocking
import java.util.*

class BackgroundServiceClass : FirebaseMessagingService() {
    private lateinit var auth: FirebaseAuth
    private val repository = TestNotificationRepository()

    // TODO : 登録トークンを取得してFirestoreにuidと一緒に登録
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        // uidを取得、uidとtokenが取得されているか確認
        val uid = auth.currentUser!!.uid
        Log.d("TOKEN", "Refreshed token: $token")
        Log.d("UID", "uid: $uid")

        // uidとtokenをFirestore(testNotification)に登録
        runBlocking {
            repository.save(uid, token)
        }.addOnCompleteListener {
            Log.d("TAG", "uid and token are saved")
        }

        // チャンネルidを設定
        addChannelId()
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        // 通知からタイトルと本文を取得
        val title: String = message?.notification?.title.toString()
        val text: String = message?.notification?.body.toString()

        // 通知表示
        sendNotification(title, text)
    }

    // 通知表示の設定
    private fun sendNotification(title: String, text: String) {
        // 通知タップ時の画面遷移を指定(Notificatonsフラグメントを表示)
        val intent = Intent(this, CountPageActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        // 通知メッセージのスタイルを設定
        val inboxStyle = NotificationCompat.InboxStyle()
        val messageArray: List<String> = text.split("\n")
        for (msg: String in messageArray) {
            inboxStyle.addLine(msg)
            Log.d("msg", msg) // CHECK: この部分は一体何が起こっているのか
        }

        // 通知音の設定
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        // 通知の見た目を設定
        val notificationBuilder =
            NotificationCompat.Builder(this, resources.getString(R.string.channel_id))
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .setSound(defaultSoundUri)

        // TODO : 通知を実施
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val uuid = UUID.randomUUID().hashCode()
        notificationManager.notify(uuid, notificationBuilder.build())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationBuilder.setChannelId(resources.getString(R.string.channel_id))
        }
    }

    // チャンネルの設定
    private fun addChannelId() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(
                resources.getString(R.string.channel_id),
                resources.getString(R.string.channel_name),
                NotificationManager.IMPORTANCE_HIGH
            )

            // ロック画面における表示レベルを設定
            channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            // チャンネル登録
            manager.createNotificationChannel(channel)
        }
    }
}