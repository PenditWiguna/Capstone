package com.ratan.maigen.view.activity

import TFLiteModelHelper
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.replace
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ratan.maigen.R
import com.ratan.maigen.databinding.ActivityMainBinding
import com.ratan.maigen.ui.home.HomeFragment
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

//        val recommendations = getRecommendationsFromPrediction(prediction)

//        val recyclerView: RecyclerView = findViewById(R.id.rv_destination)
//        recyclerView.layoutManager = LinearLayoutManager(this)


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
                (token)
            }
        }

        val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)

        if (!isLoggedIn) {
            navigateToLoginActivity()
            return
        }

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.nav_host_fragment_activity_main, HomeFragment())
                addToBackStack(null)
            }
        }
    }

    private fun clearSession() {
        val sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    private fun navigateToLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

//    private fun checkSurveyPreferenceAndNavigate(token: String) {
//        viewModel.getSurveyPreference().observe(this) { isSurveyCompleted ->
//            if (isSurveyCompleted) {
//                getData(token)
//            } else {
//                navigateToSurveyPreferenceActivity()
//            }
//        }
//    }

//    private fun getData(token: String) {
//        val adapter = DestinationAdapter(this)
//        binding.rvDestination.adapter = adapter.withLoadStateFooter(
//            footer = LoadingStateAdapter {
//                adapter.retry()
//            }
//        )
//        viewModel.getDestination(token).observe(this) {
//            adapter.submitData(lifecycle, it)
//            showLoading(false)
//        }
//    }

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