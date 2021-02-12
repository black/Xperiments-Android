package ca.nuro.nuos.viewmodellivedatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import androidx.activity.viewModels
import ca.nuro.nuos.viewmodellivedatabase.factory.FakeViewModel
import ca.nuro.nuos.viewmodellivedatabase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val TAG:String = "DataFlag"
    private lateinit var binding: ActivityMainBinding

    /*ViewModels*/
    private val fakeViewModel: FakeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observers()
    }

    private fun observers(){
        fakeViewModel.getDataList().observe(this,{ data->
            Log.d(TAG,data.toString())
        })
    }
}