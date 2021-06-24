package com.black.recyclerviewiteminvertcolor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.black.recyclerviewiteminvertcolor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var rvAdapter: RVTileAdapter? = null
    private var tileList = arrayListOf<Items>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvAdapter = RVTileAdapter(this, 3, 16, R.layout.tile_item, tileList)
        val mLayoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = rvAdapter
        binding.recyclerView.layoutManager = mLayoutManager
        binding.recyclerView.addItemDecoration(
            SpacesItemDecoration(
                2,
                resources.getDimensionPixelSize(R.dimen.spacing),
                false
            )
        )

        rvAdapter?.setOnItemClickListener(object:OnItemClickListener{
            override fun onItemClick(pos: Int) {
                Log.d("TILETAG","Clicked $pos")
                highLight(pos)
            }

        })
    }

    override fun onResume() {
        super.onResume()
        dummyData()
    }

    private fun dummyData(){
        tileList.add(Items("Yes", "yes", "https://pics.freeicons.io/uploads/icons/png/1114591141536572527-512.png"))
        tileList.add(Items("No", "no", "https://pics.freeicons.io/uploads/icons/png/15680148761557749687-512.png"))
        tileList.add(Items("Help", "I need help", "https://pics.freeicons.io/uploads/icons/png/20136677451543238895-512.png"))
        tileList.add(Items("Hot", "I am feeling hot", "https://pics.freeicons.io/uploads/icons/png/12105862511596027200-512.png"))
        tileList.add(Items("Cold", "I am feeling cold", "https://pics.freeicons.io/uploads/icons/png/6003215611595989181-512.png"))
        tileList.add(Items("Restroom", "I need to go to the restroom", "https://pics.freeicons.io/uploads/icons/png/13814308451552641730-512.png"))
        tileList.add(Items("Hungry", "I am hungry", "https://pics.freeicons.io/uploads/icons/png/16538534611595501138-512.png"))
        tileList.add(Items("Water", "I need water", "https://pics.freeicons.io/uploads/icons/png/6841325771595452640-512.png"))
        rvAdapter?.notifyDataSetChanged()
    }

    private fun highLight(pos:Int){
        binding.recyclerView.getChildAt(pos).isEnabled = true
        binding.recyclerView.getChildAt(pos).findViewById(R.id.resp_img).
    }
}