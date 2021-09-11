package com.prasannakumar.dindinnassignment

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
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
import android.content.Intent




class MainActivity : AppCompatActivity(), FoodListAdapter.ItemClickListener,
    CountdownRunnable.RunnableListener {
    private val TAG = "ABC"
    private lateinit var viewModel: MainViewModel
    lateinit var binding: ActivityMainBinding
    private lateinit var listAdapter: FoodListAdapter
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
       // navController = Navigation.findNavController(this,R.id.nav_host_fragment)
        setContentView(binding.root)
        setupViewModel()
        setupUI()
    }
    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this, ViewModelFactory(RetrofitBuilder.ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
        viewModel.getFoodOrder().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        Log.d("ABC", "setupViewModel:SUCCESS ")
                        resource.data?.let { users -> getFoodData(users) }
                        binding.scrollView.albumList.visibility = View.VISIBLE
                        binding.scrollView.progressBar.visibility = View.GONE

                    }
                    Status.ERROR -> {
                        Log.d("ABC", "setupViewModel:ERROR ${resource.message}")
                        binding.scrollView.albumList.visibility = View.VISIBLE
                        binding.scrollView.progressBar.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        Log.d("ABC", "setupViewModel:LOADING ")
                        binding.scrollView.albumList.visibility = View.GONE
                        binding.scrollView.progressBar.visibility = View.GONE
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
        binding.scrollView.albumList.layoutManager = linearLayoutManager
        binding.scrollView.albumList.adapter = listAdapter

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
      //  navController.navigate(R.id.ingredientActivity)
        Log.d(TAG, "itemClicked: clicked")
        val i = Intent(applicationContext, TabActivity::class.java)
        startActivity(i)
    }

    override fun removeView(tag: Any, pos: Int) {
        listAdapter.removeView(tag, pos)
    }

    override fun ringBell() {
        var mp = MediaPlayer.create(this, R.raw.alert);
        mp.setOnPreparedListener { mp -> mp.start() }
        mp.setOnCompletionListener {
            mp.reset();
            mp.release();
            mp = null;
        }
    }
}