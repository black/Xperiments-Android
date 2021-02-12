package ca.nuro.nuos.viewmodellivedatabase.children

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.nuro.nuos.viewmodellivedatabase.R
import ca.nuro.nuos.viewmodellivedatabase.ViewBindingHolder
import ca.nuro.nuos.viewmodellivedatabase.ViewBindingHolderImpl
import ca.nuro.nuos.viewmodellivedatabase.databinding.FragmentBearBinding

class BearFragment : Fragment(R.layout.fragment_bear) , ViewBindingHolder<FragmentBearBinding> by ViewBindingHolderImpl() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = initBinding(FragmentBearBinding.inflate(layoutInflater), this) {


    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = BearFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}