package com.example.bai10

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.widget.Toast


class MySmsReceive : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        processReceive(context, intent)
    }

    fun processReceive(context: Context?, intent: Intent) {
        val extras = intent.getExtras()
        var message = ""
        var body: String? = ""
        var address: String? = ""
        if (extras != null) {
            val smsEtra = extras.get("pdus") as Array<Any?>?
            for (i in smsEtra!!.indices) {
                val sms: SmsMessage = SmsMessage.createFromPdu(smsEtra[i] as ByteArray?)
                body = sms.getMessageBody()
                address = sms.getOriginatingAddress()
                message += "Có 1 tin nhắn từ " + address + "\n" + body + " vừa gởi đến"
            }

//Hiển thị
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
}