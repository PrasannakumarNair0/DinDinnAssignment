package com.prasannakumar.dindinnassignment.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.prasannakumar.dindinnassignment.adapters.IngredientAdapter
import com.prasannakumar.dindinnassignment.dataClass.Ingredient
import com.prasannakumar.dindinnassignment.dataClass.ProductList
import com.prasannakumar.dindinnassignment.databinding.FragmentDynamicLayoutBinding
import com.prasannakumar.dindinnassignment.models.DynamicFragmentViewModel
import com.prasannakumar.dindinnassignment.models.SearchViewModel
import java.util.*

const val POSITION = "position"
const val NOT_DATA_FOUND = "No Data Found.."
const val TOTAL_COLUMN = 2

class DynamicFragment : Fragment() {
    private var pos = 0
    private var users: List<Ingredient> = ArrayList()
    private lateinit var adapter: IngredientAdapter
    private lateinit var binding: FragmentDynamicLayoutBinding
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var dynamicViewModel: DynamicFragmentViewModel
    private lateinit var snackBar: Snackbar
    private lateinit var container: ViewGroup


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (container != null) {
            this.container = container
        }
        binding = FragmentDynamicLayoutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        searchViewModel = ViewModelProvider(requireActivity())[SearchViewModel::class.java]
        pos = requireArguments().getInt(POSITION, 0)
        binding.progressBar.visibility = View.GONE
        binding.albumList.visibility = View.VISIBLE
        binding.albumList.layoutManager = GridLayoutManager(activity, TOTAL_COLUMN)
        adapter = IngredientAdapter(users[pos].productList as ArrayList<ProductList>)
        binding.albumList.adapter = adapter

        searchViewModel.getQuery()
            ?.observe(viewLifecycleOwner, {
                it?.let { resource ->
                    dynamicViewModel.filterValues(resource, users[pos].productList)

                }
            })


    }

    private fun setupViewModel() {
        dynamicViewModel =
            ViewModelProvider(requireActivity())[DynamicFragmentViewModel::class.java]
        dynamicViewModel.getFilterValue().observe(viewLifecycleOwner, {
            it?.let { resource ->
                filterCheck(resource)
            }
        })
    }

    companion object {
        fun addFragment(pos: Int, users: List<Ingredient>): DynamicFragment {
            val fragment = DynamicFragment()
            val args = Bundle()
            args.putInt(POSITION, pos)
            fragment.setList(users)
            fragment.arguments = args
            return fragment
        }
    }

    fun setList(users: List<Ingredient>) {
        this.users = users
    }

    private fun filterCheck(filterList: ArrayList<ProductList>) {
        if (filterList.isEmpty()) {
            val filterListCopy = ArrayList<ProductList>()
            adapter.filterList(filterListCopy)
            showSnackBar(NOT_DATA_FOUND)
        } else {
            adapter.filterList(filterList)
        }
    }

    private fun showSnackBar(data: String) {
        snackBar = Snackbar.make(container, data, Snackbar.LENGTH_LONG)
        snackBar.show()
    }
}
