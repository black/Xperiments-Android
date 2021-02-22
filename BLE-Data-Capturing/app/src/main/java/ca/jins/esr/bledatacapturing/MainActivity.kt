package ca.jins.esr.bledatacapturing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ca.jins.esr.bledatacapturing.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}