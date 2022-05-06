package ca.nuro.nuos.headphoneswitch

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class SwitchBroadcastReceiver: BroadcastReceiver() {
    private val CLICK_DELAY: Long = 500
    private var lastClick: Long = 0 // oldValue
    private var currentClick = System.currentTimeMillis()

    override fun onReceive(ctx: Context?, intent: Intent?) {
        if (intent?.action.equals(Intent.ACTION_HEADSET_PLUG)) {
            if (intent?.extras?.getInt("state") == 1) Toast.makeText(ctx,"Plugged in",Toast.LENGTH_LONG).show()
            else Toast.makeText(ctx, "earphones un-plugged",Toast.LENGTH_LONG).show()
        }
    }
}