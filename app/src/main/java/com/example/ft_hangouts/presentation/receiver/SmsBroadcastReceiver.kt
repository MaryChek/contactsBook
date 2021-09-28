package com.example.ft_hangouts.presentation.receiver

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.ft_hangouts.data.room.model.ChatMessage
import com.example.ft_hangouts.domain.models.UserType
import com.example.ft_hangouts.presentation.App
import com.example.ft_hangouts.presentation.receiver.model.Sms
import java.lang.IllegalArgumentException

class SmsBroadcastReceiver : BroadcastReceiver() {

    private val logTag: String = this::class.java.simpleName

    private var smsListener: SmsReceiveListener? = null

    fun setOnSmsReceiveListener(func: SmsReceiveListener) {
        smsListener = func
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == SMS_RECEIVED) {
            val bundle = intent.extras
            if (bundle != null) {
                val pdus = bundle[ARG_SMS_KEY] as Array<*>?
                if (pdus?.isNotEmpty() == true) {
                    val newMessage = getMessage(pdus)
                    newMessage?.let {
                        smsListener?.onSmsReceive(newMessage)
                    }
                }
            }
        }
    }

    private fun getMessage(argument: Array<*>): Sms? {
        val messages = arrayOfNulls<SmsMessage>(argument.size)

        val sb = StringBuilder()
        for (i in argument.indices) {
            messages[i] = SmsMessage.createFromPdu(argument[i] as ByteArray)
            sb.append(messages[i]?.messageBody)
        }
        val number = messages[0]?.originatingAddress
        return if (number == null) {
            Log.e(logTag, "number is missing", IllegalArgumentException())
            null
        } else {
            val message = sb.toString()
            val newMessage = Sms(number, message)
            newMessage
        }
    }

    companion object {
        private const val ARG_SMS_KEY = "pdus"
        private const val SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED"
    }
}