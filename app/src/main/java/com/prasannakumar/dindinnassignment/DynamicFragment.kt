package com.prasannakumar.dindinnassignment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.prasannakumar.dindinnassignment.adapters.IngredientAdapter
import com.prasannakumar.dindinnassignment.dataClass.Ingredient
import com.prasannakumar.dindinnassignment.dataClass.ProductList
import com.prasannakumar.dindinnassignment.databinding.FragmentDynamicLayoutBinding
import com.prasannakumar.dindinnassignment.models.SearchViewModel
import java.util.*

const val POSITION="position"
class DynamicFragment : Fragment() {
    private var pos = 0
    private var users: List<Ingredient> = ArrayList()
    private lateinit var adapter: IngredientAdapter
    private lateinit var binding: FragmentDynamicLayoutBinding
    private lateinit var searchViewModel: SearchViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDynamicLayoutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchViewModel = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
        pos = requireArguments().getInt(POSITION, 0)
        binding.progressBar.visibility = View.GONE
        binding.albumList.visibility = View.VISIBLE
        binding.albumList.layoutManager = GridLayoutManager(activity, 2)
        adapter = IngredientAdapter(users.get(pos).productList as ArrayList<ProductList>)
        binding.albumList.adapter = adapter

        searchViewModel.getQuery()
            ?.observe(viewLifecycleOwner, {
                it?.let { resource ->
                    filter(resource)
                }
            })

    }

    fun setList(users: List<Ingredient>) {
        this.users = users
    }

    companion object {
        private const val TAG = "ABC"
        fun addFragment(pos: Int, users: List<Ingredient>): DynamicFragment {
            val fragment = DynamicFragment()
            val args = Bundle()
            args.putInt(POSITION, pos)
            fragment.setList(users)
            fragment.arguments = args
            return fragment
        }
    }

    private fun filter(text: String?) {
        val filterList = ArrayList<ProductList>()
        for (item in users[pos].productList) {
            if (item.productName.toLowerCase(Locale.ROOT).contains(text!!.toLowerCase(Locale.ROOT))) {

                filterList.add(item);
            }
        }
        if (filterList.isEmpty()) {
            Toast.makeText(activity, "No Data Found..", Toast.LENGTH_SHORT).show();

        } else {
            adapter.filterList(filterList);
        }
    }
}
