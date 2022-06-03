package com.black.xperiments.kotlin_viewmodel_livedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.ui.AppBarConfiguration
import com.black.xperiments.kotlin_viewmodel_livedata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val dataViewModel: DataViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observers()
    }

    private fun observers(){
        dataViewModel.dataobj.observe(this){
            binding.dataobjDataView.text = "${it.name} ${it.age}"
        }

        dataViewModel.randomdata.observe(this){
            binding.randomDataView.text = "$it"
        }
    }

    fun updateDataObj(view: View) {
        dataViewModel.dataobj.value = DataObj("Rad",45)
    }
    fun updateRandom(view: View) {
        dataViewModel.randomdata.value = (Math.random()*100).toInt()
    }
}