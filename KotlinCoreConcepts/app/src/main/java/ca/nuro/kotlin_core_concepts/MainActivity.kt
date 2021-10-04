package ca.nuro.kotlin_core_concepts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import ca.nuro.kotlin_core_concepts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = "KOTLIN_CONCEPT"
    private var bear:Int? = null
    private var somePerson:Person? = null
    private val numList:MutableList<Int> = mutableListOf(1,2,3,5,6)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        updateLetConcept("$bear")
        updateAlsoConcept("$numList")
    }

    /* Let Concept*/
    fun letUpdate(view: View) {
        val x = bear?.let {
            it+1
        } // else if value is null
        updateLetConcept("$x")
        Log.d(TAG,"$x")
        bear = (Math.random()*100).toInt()
    }

    private fun updateLetConcept(msg:String){
        binding.letConcept.text = msg
    }

    /* Also Concept*/
    fun alsoUpdate(view: View) {
        val tempList = numList.also {
            it.add(7) // do this
            it.remove(3) // also do this
            it.removeAt(1) // also do this
        }
        updateAlsoConcept("$tempList")
    }

    private fun updateAlsoConcept(msg:String){
        binding.alsoConcept.text = msg
    }


    /* With Concept*/
    fun withUpdate(view: View) {
        val person = Person()
        val bio:String = with(person){
            Log.d(TAG,"With Name ${this.name} Age ${this.age}")
            "I am ${this.name} and my age ${this.age}" // <-- gets return
        }
        binding.withConcept.text = bio
    }

    /* Apply Concept*/
    fun applyUpdate(view: View) {
       val person = Person()
       person.apply {
           name = "Wanchung Bhutia"
           age = 56
       }
        binding.applyConcept.apply {
            text = "I am ${person.name} and my age ${person.age}"
        }
    }

    /* Run Concept*/
    fun runUpdate(view: View) {
        val bio = somePerson?.run {
            name = "RunPerson User"
            age = 78
            this
        }

        binding.runConcept.apply {
            text = "I am ${bio?.name} and my age ${bio?.age}"
        }

        // before it is run so text will set null values
        somePerson = Person()
        // once clicked somePreson object gets assigned to Person Object which is not null anymore
    }


}