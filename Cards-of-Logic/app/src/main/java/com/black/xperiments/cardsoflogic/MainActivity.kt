package com.black.xperiments.cardsoflogic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.recyclerview.widget.DefaultItemAnimator
import com.black.xperiments.cardsoflogic.databinding.ActivityMainBinding
import com.black.xperiments.cardsoflogic.logic.Cards
import com.black.xperiments.cardsoflogic.logic.LogicAdapter
import com.black.xperiments.cardsoflogic.logic.OnCardClickListener
import com.yuyakaido.android.cardstackview.*
import org.json.JSONArray
import java.io.IOException


class MainActivity : AppCompatActivity(), CardStackListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var cslm: CardStackLayoutManager
    private var logicAdapter: LogicAdapter?=null
    private var logicList = arrayListOf<Cards>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cslm = CardStackLayoutManager(this, this).apply {
            setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
            setOverlayInterpolator(LinearInterpolator())
            setStackFrom(StackFrom.Bottom)
            setVisibleCount(3)
            setTranslationInterval(8.0f)
        }

        logicAdapter = LogicAdapter(this,  logicList)
        binding.stackView.apply {
            layoutManager = cslm
            adapter = logicAdapter
            itemAnimator.apply {
                if (this is DefaultItemAnimator) {
                    supportsChangeAnimations = false
                }
            }
        }

        logicAdapter?.setOnItemClickListener(object: OnCardClickListener {
            override fun onItemClick(pos: Int) {
                Log.d("MYLOS","${logicList[pos]}")
                seeFullView(logicList[pos])
            }

            override fun onLongItemClick(pos: Int) {

            }

        })
        loadJSONFromAsset()
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {

    }

    override fun onCardSwiped(direction: Direction?) {

    }

    override fun onCardRewound() {

    }

    override fun onCardCanceled() {

    }

    override fun onCardAppeared(view: View?, position: Int) {

    }

    override fun onCardDisappeared(view: View?, position: Int) {

    }

    private var fileName = "cardsoflogic.json"

    private fun loadJSONFromAsset(){
        try {
            val inputStream = assets.open(fileName)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val jsonArray = JSONArray(String(buffer))
            for (i in 0..10){
                val singleObject = jsonArray.getJSONObject(i)
                val card = Cards()
                card.title = singleObject.get("title").toString()
                card.explanation = singleObject.get("explanation").toString()
                card.example = singleObject.get("example").toString()
                card.conclusion = singleObject.get("conclusion").toString()
                logicList.add(card)
                logicAdapter?.notifyItemChanged(i)
            }
//            string = String(buffer)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun seeFullView(card: Cards){
        val intent = Intent(this, AboutActivity::class.java)
        intent.putExtra("card", card)
        startActivity(intent)
    }
}