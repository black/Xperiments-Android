package ca.nuro.nuos.interfacelivedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import ca.nuro.nuos.interfacelivedata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val handler: Handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* Demo 1 */
        val dummy = Dummy(handler)
        dummy.getData(object :DataInterface{
            override fun onData(data: Int) {
                 binding.dataLive.text = "Live $data"
                 /*
                 toast is causing error so better use ui elements, this is I wanted to check
                 Toast.makeText(applicationContext,data.toString(),Toast.LENGTH_LONG).show()
                 */
            }
        })

        /* Demo 2 */
        var i = 0
        handler.post(object:Runnable{
            override fun run() {
                dummy.getTrigData(i,object :DataInterface{
                    override fun onData(data: Int) {
                        binding.dataTrigLive.text = "Live Trig $data"
                    }
                })
                i++
                handler.postDelayed(this,1000)
            }
        })
    }

    override fun onStop() {
        super.onStop()
    }
}