package com.prasannakumar.dindinnassignment

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.prasannakumar.dindinnassignment.adapters.FoodListAdapter
import com.prasannakumar.dindinnassignment.data.api.RetrofitBuilder
import com.prasannakumar.dindinnassignment.dataClass.OrderList
import com.prasannakumar.dindinnassignment.databinding.ActivityMainBinding
import com.prasannakumar.dindinnassignment.models.MainViewModel
import com.prasannakumar.dindinnassignment.models.ViewModelFactory
import com.prasannakumar.dindinnassignment.utils.CountdownRunnable
import com.prasannakumar.dindinnassignment.utils.Status
import java.util.*


class MainActivity : AppCompatActivity(), FoodListAdapter.ItemClickListener,
    CountdownRunnable.RunnableListener {
    private lateinit var viewModel: MainViewModel
    lateinit var binding: ActivityMainBinding
    private lateinit var listAdapter: FoodListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        setupUI()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this, ViewModelFactory(RetrofitBuilder.ApiHelper(RetrofitBuilder.apiService))
        )[MainViewModel::class.java]
        viewModel.getFoodOrder().observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { users -> getFoodData(users) }
                        binding.recyclerView.albumList.visibility = View.VISIBLE
                        binding.recyclerView.progressBar.visibility = View.GONE

                    }
                    Status.ERROR -> {
                        binding.recyclerView.albumList.visibility = View.VISIBLE
                        binding.recyclerView.progressBar.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        binding.recyclerView.albumList.visibility = View.GONE
                        binding.recyclerView.progressBar.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun setupUI() {
        listAdapter = FoodListAdapter(arrayListOf())
        listAdapter.onItemClickListener = this
        listAdapter.onViewRunnableListener = this
        val linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.albumList.layoutManager = linearLayoutManager
        binding.recyclerView.albumList.adapter = listAdapter

    }

    private fun getFoodData(foodObj: List<OrderList>) {
        listAdapter.apply {
            addItem(foodObj)
            notifyDataSetChanged()
        }

    }

    override fun removeItem(users: ArrayList<OrderList>, itemView: Int, tag: Any) {
        listAdapter.removeItem(users, itemView, tag)

    }

    override fun itemClicked() {
        val i = Intent(applicationContext, TabActivity::class.java)
        startActivity(i)
    }

    override fun removeView(tag: Any, pos: Int) {
        listAdapter.removeView(tag, pos)
    }

    override fun ringBell() {
        var mp = MediaPlayer.create(this, R.raw.alert)
        mp.setOnPreparedListener {  mp.start() }
        mp.setOnCompletionListener {
            mp.reset()
            mp.release()
            mp = null
        }
    }
}