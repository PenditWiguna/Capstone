package com.ratan.maigen.view.activity

import TFLiteModelHelper
import android.annotation.SuppressLint
import android.content.Intent
import android.gesture.Prediction
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ratan.maigen.R
import com.ratan.maigen.databinding.ActivityMainBinding
import com.ratan.maigen.view.adapter.DestinationAdapter
import com.ratan.maigen.view.adapter.LoadingStateAdapter
import com.ratan.maigen.view.viewmodel.MainViewModel
import com.ratan.maigen.view.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var checkBoxNature: CheckBox
    private lateinit var checkBoxCulinary: CheckBox
    private lateinit var checkBoxSocial: CheckBox
    private lateinit var checkBoxSea: CheckBox
    private lateinit var buttonSubmit: Button
    private lateinit var textViewResult: TextView


    private lateinit var modelHelper: TFLiteModelHelper

    private val viewModel: MainViewModel by viewModels { ViewModelFactory.getInstance(this) }
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkBoxNature = findViewById(R.id.checkBoxNature)
        checkBoxCulinary = findViewById(R.id.checkBoxCulinary)
        checkBoxSocial = findViewById(R.id.checkBoxSocial)
        checkBoxSea = findViewById(R.id.checkBoxSea)
        buttonSubmit = findViewById(R.id.buttonSubmit)
        textViewResult = findViewById(R.id.textViewResult)

        modelHelper = TFLiteModelHelper(this)

        buttonSubmit.setOnClickListener {
            val input = FloatArray (4)
            input[0] = if (checkBoxNature.isChecked) 1.0f else 0.0f
            input[1] = if (checkBoxCulinary.isChecked) 1.0f else 0.0f
            input[2] = if (checkBoxSocial.isChecked) 1.0f else 0.0f
            input[3] = if (checkBoxSea.isChecked) 1.0f else 0.0f

            val prediction = modelHelper.predict(input)
            displayResult(prediction)
        }







        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
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
    }

    fun displayResult(prediction: FloatArray){

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
        when(item.itemId) {
            R.id.logout ->viewModel.logout()
        }
        return super.onOptionsItemSelected(item)
    }
}