package ca.nuro.nuos.spinnertobytearray

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import ca.nuro.nuos.spinnertobytearray.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.spinnerSelectMode.setSelection(1)
        binding.spinnerSelectMode.onItemSelectedListener =  object:
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var vass = getItemMode()
                Log.d("SpinnerVal",  "Value: "+ vass[0] + "\t" +vass[1] )
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
    }

    private fun getItemMode(): ByteArray {
        val bytes = ByteArray(2)
        bytes[0] =  (binding.spinnerSelectMode.selectedItemPosition and 0xFF).toByte()
        bytes[1] =  (binding.spinnerSelectQuality.selectedItemPosition and 0xFF).toByte()
        return bytes
    }

    private fun getItemPrams(): ByteArray {
        val bytes = ByteArray(2)
        bytes[0] =  (binding.spinnerSelectAcceleration.selectedItemPosition and 0xFF).toByte()
        bytes[1] =  (binding.spinnerSelectGyro.selectedItemPosition and 0xFF).toByte()
        return bytes
    }

    fun setItemMode(item_mode: Byte, item_quality: Byte) {
        handler.post(Runnable {
            var spinner: Spinner? = null
            binding.spinnerSelectMode.setSelection(item_mode.toInt())
            binding.spinnerSelectQuality.setSelection(item_quality.toInt())
        })
    }

    fun setItemParams(item_acc: Byte, item_ang: Byte) {
        handler.post {
            binding.spinnerSelectAcceleration.setSelection(item_acc.toInt())
            binding.spinnerSelectGyro.setSelection(item_ang.toInt())
        }
    }


}

/*
* It sets result to the (unsigned) value resulting from putting the 8 bits of value in the lowest 8 bits of result.

The reason something like this is necessary is that byte is a signed type in Java. If you just wrote:

int result = value;
then result would end up with the value ff ff ff fe instead of 00 00 00 fe. A further subtlety is that the & is defined to operate only on int values1, so what happens is:

value is promoted to an int (ff ff ff fe).
0xff is an int literal (00 00 00 ff).
The & is applied to yield the desired value for result.
(The point is that conversion to int happens before the & operator is applied.)
 */