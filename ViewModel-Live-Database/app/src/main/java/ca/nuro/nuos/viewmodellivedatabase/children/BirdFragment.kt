package ca.nuro.nuos.viewmodellivedatabase.children

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.nuro.nuos.viewmodellivedatabase.R
import ca.nuro.nuos.viewmodellivedatabase.ViewBindingHolder
import ca.nuro.nuos.viewmodellivedatabase.ViewBindingHolderImpl
import ca.nuro.nuos.viewmodellivedatabase.databinding.FragmentBirdBinding

class BirdFragment : Fragment(R.layout.fragment_bird) , ViewBindingHolder<FragmentBirdBinding> by ViewBindingHolderImpl()  {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = initBinding(FragmentBirdBinding.inflate(layoutInflater), this) {

    }

    companion object {

        @JvmStatic
        fun newInstance() = BirdFragment().apply {
                arguments = Bundle().apply {
                }
        }
    }
}