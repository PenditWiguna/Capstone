package com.ratan.maigen.view.activity

import TFLiteModelHelper
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ratan.maigen.R
import com.ratan.maigen.databinding.ActivityMainBinding
import com.ratan.maigen.view.adapter.DestinationAdapter
import com.ratan.maigen.view.adapter.LoadingStateAdapter
import com.ratan.maigen.view.viewmodel.MainViewModel
import com.ratan.maigen.view.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var modelHelper: TFLiteModelHelper
    private lateinit var destinationAdapter: DestinationAdapter

    private val viewModel: MainViewModel by viewModels { ViewModelFactory.getInstance(this) }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prediction = intent.getFloatArrayExtra("prediction")

        val recommendations = getRecommendationsFromPrediction(prediction)

        val recyclerView: RecyclerView = findViewById(R.id.rv_destination)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = DestinationAdapter(recommendations)




        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        showLoading(true)
        viewModel.getSession().observe(this) { user ->
            val token = user.token
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
            getData(token)
        }

//        val predictions = intent.getStringArrayListExtra("predictions")?: arrayListOf()
//        destinationAdapter = DestinationAdapter(predictions)

    }

    private fun getData(token: String) {
        val adapter = DestinationAdapter()
        binding.rvDestination.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        viewModel.getDestination(token).observe(this) {
            adapter.submitData(lifecycle, it)
            showLoading(false)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> viewModel.logout()
        }
        return super.onOptionsItemSelected(item)
    }


}