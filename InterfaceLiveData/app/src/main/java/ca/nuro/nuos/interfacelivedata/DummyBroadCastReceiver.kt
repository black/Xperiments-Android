package ca.nuro.nuos.interfacelivedata

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_HEADSET_PLUG
import android.widget.Toast

class DummyBroadCastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (ACTION_HEADSET_PLUG == intent?.action){
            Toast.makeText(context, "Headset plugged in", Toast.LENGTH_LONG).show()
        }
    }
}