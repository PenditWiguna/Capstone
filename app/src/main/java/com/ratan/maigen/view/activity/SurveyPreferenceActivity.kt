package com.ratan.maigen.view.activity

import TFLiteModelHelper
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ratan.maigen.R
import com.ratan.maigen.view.viewmodel.MainViewModel
import com.ratan.maigen.view.viewmodel.ViewModelFactory
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class SurveyPreferenceActivity : AppCompatActivity() {

    private lateinit var radioButtonAgrowisata: RadioButton
    private lateinit var radioButtonAlam: RadioButton
    private lateinit var radioButtonBelanja: RadioButton
    private lateinit var radioButtonBudaya: RadioButton
    private lateinit var radioButtonCagarAlam: RadioButton
    private lateinit var radioButtonPantai: RadioButton
    private lateinit var radioButtonRekreasi: RadioButton
    private lateinit var radioButtonReligius: RadioButton
    private lateinit var buttonSubmit: Button

    private val viewModel: MainViewModel by viewModels { ViewModelFactory.getInstance(this) }
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey_preference)

        radioButtonAgrowisata = findViewById(R.id.radioButtonAgrowisata)
        radioButtonAlam = findViewById(R.id.radioButtonAlam)
        radioButtonBelanja = findViewById(R.id.radioButtonBelanja)
        radioButtonBudaya = findViewById(R.id.radioButtonBudaya)
        radioButtonCagarAlam = findViewById(R.id.radioButtonCagarAlam)
        radioButtonPantai = findViewById(R.id.radioButtonPantai)
        radioButtonRekreasi = findViewById(R.id.radioButtonRekreasi)
        radioButtonReligius = findViewById(R.id.radioButtonReligius)
        buttonSubmit = findViewById(R.id.buttonSubmit)

        val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
        token = sharedPreferences.getString("auth_token", "") ?: ""

        buttonSubmit.setOnClickListener {
            val preferences = getPreferences()
            viewModel.saveSurveyPreference(true)
            fetchRecommendations(preferences)
        }

        val imageButton: ImageButton = findViewById(R.id.next_button)
        imageButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getPreferences(): FloatArray {
        return FloatArray(8).apply {
            this[0] = if (radioButtonAgrowisata.isChecked) 1.0f else 0.0f
            this[1] = if (radioButtonAlam.isChecked) 1.0f else 0.0f
            this[2] = if (radioButtonBelanja.isChecked) 1.0f else 0.0f
            this[3] = if (radioButtonBudaya.isChecked) 1.0f else 0.0f
            this[4] = if (radioButtonCagarAlam.isChecked) 1.0f else 0.0f
            this[5] = if (radioButtonPantai.isChecked) 1.0f else 0.0f
            this[6] = if (radioButtonRekreasi.isChecked) 1.0f else 0.0f
            this[7] = if (radioButtonReligius.isChecked) 1.0f else 0.0f
        }
    }

    private fun fetchRecommendations(preferences: FloatArray) {
        val modelHelper = TFLiteModelHelper(this, onError = { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        })
        val prediction = modelHelper.predict(preferences)
        val selectedCategory = getSelectedCategory(preferences)

        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://flask-bali-destination-ilmyfxcvzq-et.a.run.app/category-recommendation?category=$selectedCategory")
            .get()
            .addHeader("Authorization", "Bearer $token")
            .build()

        Log.d("API_REQUEST", "Request URL: ${request.url}")
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("API_REQUEST", "Request failed: ${e.message}")
                runOnUiThread {
                    Toast.makeText(this@SurveyPreferenceActivity, "Failed to fetch recommendations", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseData = response.body?.string()
                    Log.d("API_REQUEST", "Response data: $responseData")
                    if (responseData != null) {
                        val recommendations = parseRecommendations(responseData)
                        runOnUiThread {
                            navigateToMainActivity(recommendations)
                        }
                    }
                } else {
                    Log.e("API_REQUEST", "Request failed with code: ${response.code}")
                    runOnUiThread {
                        Toast.makeText(this@SurveyPreferenceActivity, "Failed with code: ${response.code}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun parseRecommendations(responseData: String): List<String> {
        val jsonObject = JSONObject(responseData)
        val jsonArray = jsonObject.getJSONArray("recommendations")
        val recommendations = mutableListOf<String>()
        for (i in 0 until jsonArray.length()) {
            recommendations.add(jsonArray.getString(i))
        }
        return recommendations
    }

    private fun getSelectedCategory(preferences: FloatArray): String {
        return when {
            preferences[0] == 1.0f -> "Agrowisata"
            preferences[1] == 1.0f -> "Alam"
            preferences[2] == 1.0f -> "Belanja"
            preferences[3] == 1.0f -> "Budaya"
            preferences[4] == 1.0f -> "Cagar Alam"
            preferences[5] == 1.0f -> "Pantai"
            preferences[6] == 1.0f -> "Rekreasi"
            preferences[7] == 1.0f -> "Religius"
            else -> "Unknown"
        }
    }

    private fun navigateToMainActivity(recommendations: List<String>) {
        val intent = Intent(this, MainActivity::class.java).apply {
            putStringArrayListExtra("recommendations", ArrayList(recommendations))
        }
        startActivity(intent)
        finish()
    }
}
