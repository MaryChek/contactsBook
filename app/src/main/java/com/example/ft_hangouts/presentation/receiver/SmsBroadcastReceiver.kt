package com.example.ft_hangouts.presentation.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.util.Log
import com.example.ft_hangouts.domain.interactors.GetMessage
import com.example.ft_hangouts.presentation.App
import com.example.ft_hangouts.presentation.receiver.model.Sms
import java.lang.IllegalArgumentException

class SmsBroadcastReceiver : BroadcastReceiver() {

    private val logTag: String = this::class.java.simpleName

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == SMS_RECEIVED) {
            val bundle = intent.extras
            if (bundle != null) {
                val pdus = bundle[ARG_SMS_KEY] as Array<*>?
                if (pdus?.isNotEmpty() == true) {
                    val newMessage = getMessage(pdus)
                    newMessage?.let { sms ->
                        onSmsReceive(sms, context)
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

    private fun onSmsReceive(sms: Sms, context: Context?) {
        val app: App? = context?.applicationContext as App?
        app?.let {
            val interactor: GetMessage = it.interactor
            interactor.getMessage(sms)
        }
    }

    companion object {
        private const val ARG_SMS_KEY = "pdus"
        private const val SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED"
    }
}