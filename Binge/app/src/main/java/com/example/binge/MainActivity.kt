package com.example.binge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.binge.databinding.ActivityMainBinding
import com.example.binge.movie.Movie
import com.example.binge.movie.MovieListAdapter
import com.example.binge.movie.OnItemSelected

class MainActivity : AppCompatActivity() {

    private val TAG = "LISTENER"

    private lateinit var binding: ActivityMainBinding
    private var movieListAdapter: MovieListAdapter?=null
    private val respArray =  arrayListOf<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        for (i in 0..8){
            respArray.add(Movie("$i","Title $i","${Math.random()*5}","",""))
        }

        movieListAdapter = MovieListAdapter(this,respArray)
        binding.movieListView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = movieListAdapter
        }

        movieListAdapter?.setOnItemClickListener(object:OnItemSelected{
            override fun onItem(pos: Int) {
                Log.d(TAG,"$pos")
            }
        })

    }
}