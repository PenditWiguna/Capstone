package com.ratan.maigen.view.activity

import TFLiteModelHelper
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
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

    private lateinit var modelHelper: TFLiteModelHelper
    private lateinit var destinationAdapter: DestinationAdapter

    private val viewModel: MainViewModel by viewModels { ViewModelFactory.getInstance(this) }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //get prediction
        val prediction = intent.getFloatArrayExtra("prediction")


        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_explore, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        showLoading(true)
        viewModel.getSession().observe(this) { user ->
            val token = user.token
            if (!user.isLogin) {
                navigateToWelcomeActivity()
            } else {
                checkSurveyPreferenceAndNavigate(token, prediction)
            }
        }

    }

    private fun checkSurveyPreferenceAndNavigate(token: String, prediction: FloatArray?) {
        viewModel.getSurveyPreference().observe(this) { isSurveyCompleted ->
            if (isSurveyCompleted) {
                if (prediction != null) {
                    val predictionList = prediction.map { it.toString() }
                    getData(token, predictionList)
                }
            } else {
                navigateToSurveyPreferenceActivity()
            }
        }
    }



    private fun getData(token: String, prediction: List<String>) {
        val adapter = DestinationAdapter(this)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.bottom_nav_menu, menu)
        return true
    }

    override fun onResume() {
        super.onResume()
        viewModel.getSession().value?.let { user ->
            if (!user.isLogin) {
                navigateToWelcomeActivity()
            } else {
                navigateToSurveyPreferenceActivity()
            }
        }
    }

    private fun navigateToWelcomeActivity() {
        startActivity(Intent(this, WelcomeActivity::class.java))
        finish()
    }

    private fun navigateToSurveyPreferenceActivity() {
        startActivity(Intent(this, SurveyPreferenceActivity::class.java))
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> showLogoutConfirmationDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLogoutConfirmationDialog() {
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Konfirmasi Logout")
        alertDialogBuilder.setMessage("Apakah Anda yakin ingin logout?")
        alertDialogBuilder.setPositiveButton("Ya") { _, _ ->
            viewModel.logout()
            showLogoutSuccessNotification()
        }
        alertDialogBuilder.setNegativeButton("Tidak") { dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun showLogoutSuccessNotification() {
        val notificationBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        notificationBuilder.setTitle("Logout Berhasil!")
        notificationBuilder.setMessage("Anda telah berhasil logout.")
        notificationBuilder.setPositiveButton("OK") { _, _ ->
            navigateToWelcomeActivity()
        }
        val notificationDialog: AlertDialog = notificationBuilder.create()
        notificationDialog.show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}