package com.black.icon.animated

import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.black.icon.animated.databinding.ActivityMainBinding
import com.black.icon.animated.extensions.GlideApp

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var index:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadGifImage(R.drawable.loading,binding.fromGif)
     //   loadAnimatedDrawable(R.drawable.loading,binding.fromAnimatedGif)
        animatedDrawable(R.drawable.animation,binding.fromAnimatedDrawable)

        binding.toggleSignals.addOnButtonCheckedListener { group, checkedId, isChecked ->
            when(checkedId){
                R.id.left->{
                    if(index>0)index--
                }
                R.id.right->{
                    if(index<4)index++
                }
            }
            animatedDrawableWithControls(R.drawable.animation,binding.fromAnimatedDrawableWithControls,index)
        }
    }

    private fun loadGifImage(url:Int,view: ImageView){
        GlideApp.with(this)
            .load(url)
            .placeholder(R.drawable.ic_baseline_image_24)
            .error(R.drawable.ic_baseline_bike_scooter_24)
            .into(view)
    }

    private fun loadAnimatedDrawable(res:Int, imgView: ImageView){
        val animatedDrawable: AnimationDrawable?
        imgView.setBackgroundResource(res)
        animatedDrawable = imgView.background as AnimationDrawable
        animatedDrawable.start()
    }

    private fun animatedDrawable(res:Int, imgView: ImageView){
        imgView.setBackgroundResource(res)
        val animatedDrawable: AnimationDrawable? = imgView.background as AnimationDrawable?
        animatedDrawable?.start()
    }

    private fun animatedDrawableWithControls(res:Int, imgView: ImageView,pos:Int){
        imgView.setBackgroundResource(res)
        val animatedDrawable: AnimationDrawable? = imgView.background as AnimationDrawable?
        //animatedDrawable?.start() // probably needed
        animatedDrawable?.selectDrawable(pos)
    }

    private fun animatedIconBuildWithCanvasToBitmap(){
        
    }
}