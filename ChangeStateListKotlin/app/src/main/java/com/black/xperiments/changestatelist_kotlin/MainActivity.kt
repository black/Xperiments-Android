package com.black.xperiments.changestatelist_kotlin

import android.graphics.drawable.StateListDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.black.xperiments.changestatelist_kotlin.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isHoverd = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.testTile.root.setOnClickListener {
            isHoverd = !isHoverd
            binding.testTile.root.isHovered = isHoverd
        }

        binding.backgroundSelector.setOnCheckedChangeListener { group, checkedId ->
            val resId = when(checkedId){
                R.id.bt0 -> R.drawable.tile_state_1
                R.id.bt1 -> R.drawable.tile_state_2
                R.id.bt2 -> R.drawable.tile_state_3
                R.id.bt3 -> R.drawable.tile_state_4
                R.id.bt4 -> R.drawable.tile_state_5
                else -> R.drawable.tile_state_default
            }
            changeHoverColor(resId,binding.testTile.root)
        }
    }


    private fun changeHoverColor(stateName:Int,view: View){
        val states = StateListDrawable()
        states.addState(
            intArrayOf(-android.R.attr.state_hovered), ContextCompat.getDrawable(this,R.drawable.tile_state_normal)
        )
        states.addState(
            intArrayOf(android.R.attr.state_hovered), ContextCompat.getDrawable(this,stateName)
        )
        states.addState(
            intArrayOf(android.R.attr.state_pressed), ContextCompat.getDrawable(this,stateName)
        )
        view.background = states
    }
}