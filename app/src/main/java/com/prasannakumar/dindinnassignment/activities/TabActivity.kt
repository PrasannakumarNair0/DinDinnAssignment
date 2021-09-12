package com.prasannakumar.dindinnassignment.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import com.prasannakumar.dindinnassignment.R
import com.prasannakumar.dindinnassignment.adapters.TabAdapter
import com.prasannakumar.dindinnassignment.data.api.RetrofitBuilderForIngredient
import com.prasannakumar.dindinnassignment.dataClass.Ingredient
import com.prasannakumar.dindinnassignment.databinding.TabMainLayoutBinding
import com.prasannakumar.dindinnassignment.models.IngredientViewModel
import com.prasannakumar.dindinnassignment.models.SearchViewModel
import com.prasannakumar.dindinnassignment.models.ViewModelFactoryForIngredient
import com.prasannakumar.dindinnassignment.utils.Status

const val HINT = "Search of an Item"

class TabActivity : AppCompatActivity() {
    private lateinit var binding: TabMainLayoutBinding
    private lateinit var adapter: TabAdapter
    private lateinit var viewModel: IngredientViewModel
    private lateinit var searchViewModel: SearchViewModel
    private var searchView: SearchView? = null

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
        searchView!!.queryHint = HINT

        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchViewModel.setQuery(newText)
                return false
            }
        })
        return true
    }


    private fun setupUI(ingredientList: List<Ingredient>) {
        for (item in ingredientList) {
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(item.tabTitle))
        }
        adapter = TabAdapter(supportFragmentManager, binding.tabLayout.tabCount, ingredientList)
        binding.viewPager.adapter = adapter
        binding.viewPager.addOnPageChangeListener(TabLayoutOnPageChangeListener(binding.tabLayout))
        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPager.currentItem = tab.position

                searchViewModel.setQuery("")
                searchView?.setQuery("", false)
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
        )[IngredientViewModel::class.java]
        viewModel.getIngredientDetails().observe(this, {
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