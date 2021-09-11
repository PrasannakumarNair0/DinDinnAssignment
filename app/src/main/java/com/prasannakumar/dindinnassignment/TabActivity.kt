package com.prasannakumar.dindinnassignment

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import com.prasannakumar.dindinnassignment.adapters.TabAdapter
import com.prasannakumar.dindinnassignment.data.api.RetrofitBuilderForIngredient
import com.prasannakumar.dindinnassignment.dataClass.Ingredient
import com.prasannakumar.dindinnassignment.dataClass.ProductList
import com.prasannakumar.dindinnassignment.databinding.TabMainLayoutBinding
import com.prasannakumar.dindinnassignment.models.IngredientViewModel
import com.prasannakumar.dindinnassignment.models.SearchViewModel
import com.prasannakumar.dindinnassignment.models.ViewModelFactoryForIngredient
import com.prasannakumar.dindinnassignment.utils.Status

const val HINT="Type name"
class TabActivity : AppCompatActivity() {
    private val TAG = "ABC"
    private lateinit var binding: TabMainLayoutBinding
    private lateinit var adapter: TabAdapter
    private lateinit var viewModel: IngredientViewModel
    private lateinit var searchViewModel: SearchViewModel
    private var searchView: SearchView?=null
    private var filteredList = ArrayList<ProductList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TabMainLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater

        inflater.inflate(R.menu.search_menu, menu)

        val searchItem: MenuItem = menu!!.findItem(R.id.actionSearch)
         searchView = searchItem.actionView as SearchView
        searchView!!.queryHint=HINT

        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchViewModel.setQuery(newText);
                return false
            }
        })
        return true
    }



    private fun setupUI(users: List<Ingredient>) {
        for (item in users) {
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(item.tabTitle))
        }
        adapter = TabAdapter(supportFragmentManager, binding.tabLayout.tabCount, users)
        binding.viewPager.setAdapter(adapter)
        binding.viewPager.addOnPageChangeListener(TabLayoutOnPageChangeListener(binding.tabLayout))
        binding.viewPager.addOnPageChangeListener(TabLayoutOnPageChangeListener(binding.tabLayout))
        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPager.setCurrentItem(tab.position)

                searchViewModel.setQuery("")
                searchView?.setQuery("", false);
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactoryForIngredient(
                RetrofitBuilderForIngredient.ApiHelper(RetrofitBuilderForIngredient.apiService)
            )
        ).get(IngredientViewModel::class.java)
        viewModel.getIngredientDetails().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { users ->
                            setupUI(users)
                        }
                        binding.progressBar.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        })
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
    }

}