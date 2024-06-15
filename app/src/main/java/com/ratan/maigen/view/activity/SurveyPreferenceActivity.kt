package com.ratan.maigen.view.activity

import TFLiteModelHelper
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ratan.maigen.R
import com.ratan.maigen.view.viewmodel.MainViewModel
import com.ratan.maigen.view.viewmodel.ViewModelFactory

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
    private lateinit var textViewResult: TextView

    private lateinit var modelHelper: TFLiteModelHelper
    private val viewModel: MainViewModel by viewModels { ViewModelFactory.getInstance(this) }

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
        textViewResult = findViewById(R.id.textViewResult)


        buttonSubmit.setOnClickListener {
            val preferences = getPreferences()
            viewModel.saveSurveyPreference(true)
            navigateToMainActivity(preferences)
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

    private fun navigateToMainActivity(preferences: FloatArray) {
        val modelHelper = TFLiteModelHelper(this, onError = { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        })
        val prediction = modelHelper.predict(preferences)
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("prediction", prediction)
        }
        startActivity(intent)
        modelHelper.close()
        finish()
    }


//    val button: Button = findViewById(R.id.buttonSubmit)
//
//        button.setOnClickListener{
//            val input = FloatArray(65)
//
//            val modelHelper = TFLiteModelHelper(this, onError = { error ->
//                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
//            })
//            val prediction = modelHelper.predict(input)
//
//            val intent = Intent(this, MainActivity::class.java)
//            intent.putExtra("prediction",prediction)
//            startActivity(intent)
//
//            modelHelper.close()
//        }
//    }

}