package ca.nuro.nuos.headphoneswitch

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import ca.nuro.nuos.headphoneswitch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    private var singleClick = 0
    private var doubleClick = 0

    private lateinit var switchReceiver: SwitchBroadcastReceiver
    private val CLICK_DELAY: Long = 500
    private var lastClick: Long = 0 // oldValue
    private var currentClick = System.currentTimeMillis()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*--- Registering Broad Cast Receiver ---*/
        switchReceiver = SwitchBroadcastReceiver()
        IntentFilter(Intent.ACTION_HEADSET_PLUG).also {
            registerReceiver(switchReceiver,it)
        }
        IntentFilter(Intent.ACTION_MEDIA_BUTTON).also {
            registerReceiver(switchReceiver,it)
        }
    }

    override fun onDestroy() {
        unregisterReceiver(switchReceiver)
        super.onDestroy()
    }

     private fun onSingle() {
        runOnUiThread {
            binding.singleClick.text = "$singleClick"
        }
        singleClick++
    }

    private fun onDouble() {
       runOnUiThread {
           binding.doubleClick.text = "$doubleClick"
       }
        doubleClick++
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_HEADSETHOOK){
            onSingle()
            return true;
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onKeyLongPress(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_HEADSETHOOK){
            onDouble()
            return true;
        }
        return super.onKeyLongPress(keyCode, event)
    }
}