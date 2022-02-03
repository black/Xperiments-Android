package com.black.xperiments.expandablerecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.black.xperiments.expandablerecyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var rvAdapter: TileAdapter?=null
    private var tileList = arrayListOf<MyContent>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvAdapter = TileAdapter(this, R.layout.layout_rv, tileList)

        binding.list.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        // Detect Click on the individual tiles
        rvAdapter?.setOnTileClickListener(object :OnClickListener{
            override fun onClick(pos: Int) {
                Log.d("SomethingTAG","${pos}")
                tileList[pos].expanded = !tileList[pos].expanded
                rvAdapter?.notifyItemChanged(pos)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        fakeData()
    }

    private fun fakeData(){
        for(i in 0..10){
            tileList.add(MyContent("Title $i","Some Description $i",Math.random()*10.0,
                (Math.random()*2000).toInt().toString()
            ))
            rvAdapter?.notifyItemChanged(i)
        }
    }
}